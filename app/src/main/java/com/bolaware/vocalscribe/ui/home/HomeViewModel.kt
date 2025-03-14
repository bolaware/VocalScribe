package com.bolaware.vocalscribe.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bolaware.data.Language
import com.bolaware.vocalscribe.ConcatenationManager
import com.bolaware.vocalscribe.LanguageInteractor
import com.bolaware.speechrecognizer.SpeechListener
import com.bolaware.speechrecognizer.SpeechRecognizer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val speechRecognizer: SpeechRecognizer,
    private val concatenationManager: ConcatenationManager,
    private val languageInteractor: LanguageInteractor
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    init {
        initializeLanguageSettings()
    }

    private fun initializeLanguageSettings() {
        viewModelScope.launch {
            languageInteractor.getSelectedLanguage().collect { language ->
                _state.update {
                    it.copy(
                        languages = languageInteractor.getSupportedLanguages().map {
                            LanguageUi(language = it, isSelected = language == it)
                        }
                    )
                }
                initializeSpeechRecognizer(language)
            }
        }
    }

    private suspend fun initializeSpeechRecognizer(language: Language) {
        _state.update { it.copy(recordState = RecordState.Idle(true)) }
        speechRecognizer.initialize(language.modelName).onFailure {
            showAlertDialogState(
                AlertDialogState(
                    title = "Error occurred!",
                    text = "Sorry. We could not initialize all necessary parameters. Please try again",
                    isDismissable = true,
                    positive = AlertDialogAction(
                        "Retry", { viewModelScope.launch { initializeSpeechRecognizer(language) } }
                    ),
                    negative = AlertDialogAction("Cancel", { dismissDialog() })
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
                    is RecordState.Idle -> {
                        startListener()
                        RecordState.Listening
                    }
                    RecordState.Listening -> {
                        stopListener()
                        RecordState.Saving
                    }
                    else -> RecordState.Idle(false)
                }
            )
        }
    }

    private fun startListener() {
        speechRecognizer.listen(object : SpeechListener {
            override fun onHypothesis(text: String) {
                concatenationManager.addString(text, true)
                _state.update { it.copy(text = concatenationManager.getConcatenatedText()) }
            }

            override fun onFinal(text: String) {
                concatenationManager.addString(text, false)
                _state.update { it.copy(text = concatenationManager.getConcatenatedText()) }
            }

            override fun onError(exception: Exception) {
                showAlertDialogState(
                    AlertDialogState(
                        title = "Error occurred!",
                        text = "Sorry. An error occurred while transcribing",
                        isDismissable = false,
                        positive = AlertDialogAction("Retry", { dismissDialog() }),
                        negative = AlertDialogAction("Cancel", { dismissDialog() })
                    )
                )
            }
        })
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
        concatenationManager.clear()
        _state.update { it.copy(recordState = RecordState.Idle(), text = "") }
    }

    fun onClearClicked() {
        concatenationManager.clear()
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