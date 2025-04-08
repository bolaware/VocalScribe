package com.bolaware.feature_home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bolaware.core_domain.TranscriptInteractor
import com.bolaware.feature_home_domain.LanguageInteractor
import com.bolaware.feature_home_domain.SpeechConcatenator
import com.bolaware.feature_home_domain.data.LanguageDomain
import com.bolaware.speechrecognizer.SpeechRecognizer
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class HomeViewModel(
    private val speechRecognizer: SpeechRecognizer,
    private val speechConcatenator: SpeechConcatenator,
    private val languageInteractor: LanguageInteractor,
    private val transcriptInteractor: TranscriptInteractor
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    private val _event = MutableSharedFlow<HomeEvent>()
    val event = _event.asSharedFlow()

    init {
        initializeLanguageSettings()
    }

    private fun initializeLanguageSettings() {
        languageInteractor.getSelectedLanguage().onEach { language ->
            _state.update { selectedLanguage ->
                selectedLanguage.copy(
                    languages = languageInteractor.getSupportedLanguages().map {
                        LanguageUi(language = it, isSelected = language == it)
                    }
                )
            }
            initializeSpeechRecognizer(language)
        }.launchIn(viewModelScope)
    }

    private suspend fun initializeSpeechRecognizer(language: LanguageDomain) {
        _state.update { it.copy(recordState = RecordState.Idle(true)) }
        speechRecognizer.initialize(language.modelName).onFailure {
            showAlertDialogState(
                AlertDialogState(
                    title = "Error occurred!",
                    text = "Sorry. We could not initialize all necessary parameters. Please try again",
                    isDismissable = true,
                    positive = AlertDialogAction(
                        "Retry"
                    ) { viewModelScope.launch { initializeSpeechRecognizer(language) } },
                    negative = AlertDialogAction("Cancel") { dismissDialog() }
                )
            )
        }
        _state.update { it.copy(recordState = RecordState.Idle(false)) }
    }

    fun onTextChanged(text: String) {
        _state.update { it.copy(text = text) }
    }


    fun onMicClicked() {
        _state.update {
            it.copy(
                recordState = when (it.recordState) {
                    is RecordState.Idle -> RecordState.Listening
                    RecordState.Listening -> RecordState.Saving
                    else -> RecordState.Idle(false)
                }
            )
        }
        when (_state.value.recordState) {
            RecordState.Listening -> startListener()
            RecordState.Saving -> stopListener()
            else -> Unit
        }
    }

    private fun startListener() {
        speechRecognizer
            .listen()
            .catch {
                showAlertDialogState(
                    AlertDialogState(
                        title = "Error occurred!",
                        text = "Sorry. An error occurred while transcribing",
                        isDismissable = false,
                        positive = AlertDialogAction("Retry") { dismissDialog() },
                        negative = AlertDialogAction("Cancel") { dismissDialog() }
                    )
                )
            }.onEach { result ->
                result.hypothesis?.let { text ->
                    speechConcatenator.addString(text, true)
                    _state.update { it.copy(text = speechConcatenator.getConcatenatedText()) }
                }
                result.finalResult?.let { text ->
                    speechConcatenator.addString(text, false)
                    _state.update { it.copy(text = speechConcatenator.getConcatenatedText()) }
                }
            }.launchIn(viewModelScope)
    }

    private fun stopListener() {
        speechRecognizer.stopListener()
    }

    private fun showAlertDialogState(alertDialogState: AlertDialogState) {
        _state.update {
            it.copy(
                alertDialogState = alertDialogState
            )
        }
    }

    fun dismissDialog() {
        _state.update { it.copy(alertDialogState = null) }
    }

    fun onSaveClicked() {
        viewModelScope.launch {
            transcriptInteractor.saveTranscript(state.value.text)
            speechConcatenator.clear()
            _state.update { it.copy(recordState = RecordState.Idle(), text = "") }
            _event.emit(HomeEvent.Snackbar("Transcript saved successfully"))
        }
    }

    fun onClearClicked() {
        speechConcatenator.clear()
        _state.update { it.copy(recordState = RecordState.Idle(), text = "") }
    }

    fun onLanguageChoiceSelected(supportedLanguage: LanguageUi) {
        viewModelScope.launch {
            languageInteractor.saveLanguageChoice(supportedLanguage.language)
        }
    }

    override fun onCleared() {
        speechRecognizer.cleanUp()
        super.onCleared()
    }
}