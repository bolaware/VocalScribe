package com.bolaware.feature_home_domain

import com.bolaware.feature_home_domain.data.LanguageDomain
import io.mockk.*
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class LanguageInteractorTest {

    private lateinit var languageInteractor: LanguageInteractor
    private val settingsManager: SettingsManager = mockk(relaxed = true)
    private val languagesRepository: LanguagesRepository = mockk()

    @Before
    fun setup() {
        languageInteractor =
            LanguageInteractor(settingsManager, languagesRepository)
    }

    @Test
    fun `getSupportedLanguages should return list from repository`() {
        val supportedLanguages = listOf(EN, DE)
        every { languagesRepository.getSupportedLanguages() } returns supportedLanguages

        val result = languageInteractor.getSupportedLanguages()

        assertEquals(supportedLanguages, result)
        verify(exactly = 1) { languagesRepository.getSupportedLanguages() }
    }

    @Test
    fun `saveLanguageChoice should store selected language`() = runTest {
        val language = DE


        languageInteractor.saveLanguageChoice(language)

        coVerify(exactly = 1) { settingsManager.saveLanguage(DE.key) }
    }

    @Test
    fun `getSelectedLanguage should return correct language from settings`() = runTest {
        val supportedLanguages = listOf(EN, DE, FR)
        every { languagesRepository.getSupportedLanguages() } returns supportedLanguages
        every { settingsManager.userLanguage } returns flowOf(DE.key)

        val result = languageInteractor.getSelectedLanguage()

        result.collect { selectedLanguage ->
            assertEquals(DE, selectedLanguage)
        }
    }

    @Test(expected = IllegalStateException::class)
    fun `getSelectedLanguage should throw exception if saved language is not supported`() = runTest {
        every { languagesRepository.getSupportedLanguages() } returns
                listOf(EN, FR)
        every { settingsManager.userLanguage } returns flowOf(DE.key) // Not in supported languages

        val result = languageInteractor.getSelectedLanguage()
        result.collect {} // This should throw an exception
    }

    companion object {
        private val EN = LanguageDomain("en", "English", "en")
        private val DE = LanguageDomain("de", "German", "de")
        private val FR = LanguageDomain("fr", "French", "fr")
    }
}
