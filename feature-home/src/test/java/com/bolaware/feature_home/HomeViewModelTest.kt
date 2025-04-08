package com.bolaware.feature_home

import app.cash.turbine.test
import com.bolaware.feature_home.ui.HomeViewModel
import com.bolaware.feature_home.ui.LanguageUi
import com.bolaware.feature_home.ui.RecordState
import com.bolaware.feature_home_domain.LanguageInteractor
import com.bolaware.feature_home_domain.SpeechConcatenator
import com.bolaware.feature_home_domain.TranscriptInteractor
import com.bolaware.feature_home_domain.data.LanguageDomain
import com.bolaware.speechrecognizer.SpeechRecognizer
import com.bolaware.speechrecognizer.SpeechResult
import io.mockk.*
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.assertEquals

class HomeViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: HomeViewModel

    private val speechRecognizer: SpeechRecognizer = mockk(relaxed = true)
    private val speechConcatenator = SpeechConcatenator()
    private val languageInteractor: LanguageInteractor = mockk(relaxed = true)
    private val transcriptInteractor: TranscriptInteractor = mockk(relaxed = true)

    @Before
    fun setup() {
        every { languageInteractor.getSelectedLanguage() } returns flowOf(LanguageDomain("en", "English", "en"))
        every { languageInteractor.getSupportedLanguages() } returns
                listOf(
                    LanguageDomain("en", "English", "en"),
                    LanguageDomain("de", "German", "de")
                )
        
        viewModel = HomeViewModel(
            speechRecognizer, 
            speechConcatenator, 
            languageInteractor, 
            transcriptInteractor
        )
    }

    @Test
    fun `should initialize with correct language settings`() = runTest {
        viewModel.state.test {
            val state = awaitItem()
            assertEquals(2, state.languages.size)
            assertEquals("en", state.languages.first { it.isSelected }.language.key)
        }
    }

    @Test
    fun `onTextChanged updates text state`() = runTest {
        val newText = "Hello World"
        viewModel.onTextChanged(newText)

        viewModel.state.test {
            assertEquals(newText, awaitItem().text)
        }
    }

    @Test
    fun `onMicClicked starts and stops listener`() = runTest {
        viewModel.onMicClicked()
        verify { speechRecognizer.listen() }

        viewModel.onMicClicked()
        verify { speechRecognizer.stopListener() }
    }

    @Test
    fun `onMicClicked starts and start recording`() = runTest {
        val recording = "first recording"

        coEvery { speechRecognizer.listen() } returns
                flowOf(SpeechResult(finalResult = "first recording")).stateIn(this)

        viewModel.onMicClicked()

        viewModel.state.test {
            val item = expectMostRecentItem()
            assertEquals(recording, item.text)
        }
    }

    @Test
    fun `onMicClicked when recording should update text correctly`() = runTest {
        val flow = flow {
            emit(SpeechResult(hypothesis = null, finalResult = "Hello world"))
            emit(SpeechResult(hypothesis = "Hello", finalResult = null))
        }
        every { speechRecognizer.listen() } returns flow

        viewModel.onMicClicked()

        assertEquals("Hello world Hello", viewModel.state.value.text)
    }

    @Test
    fun `onSaveClicked saves transcript and clears state`() = runTest {
        val transcriptText = "Saved transcript"

        viewModel.onTextChanged(transcriptText)
        viewModel.onSaveClicked()

        coVerify { transcriptInteractor.saveTranscript(transcriptText) }

        viewModel.state.test {
            val state = awaitItem()
            assertEquals("", state.text)
            assert(state.recordState is RecordState.Idle)
        }
    }

    @Test
    fun `onClearClicked clears text and resets state`() = runTest {
        viewModel.onTextChanged("Some text")
        viewModel.onClearClicked()

        viewModel.state.test {
            val state = awaitItem()
            assertEquals("", state.text)
            assert(state.recordState is RecordState.Idle)
        }
    }

    @Test
    fun `onLanguageChoiceSelected updates selected language`() = runTest {
        val selectedLanguage = LanguageUi(LanguageDomain("de", "German", "de"), true)
        viewModel.onLanguageChoiceSelected(selectedLanguage)

        coVerify { languageInteractor.saveLanguageChoice(selectedLanguage.language) }
    }

    @After
    fun teardown() {
        clearAllMocks()
    }
}
