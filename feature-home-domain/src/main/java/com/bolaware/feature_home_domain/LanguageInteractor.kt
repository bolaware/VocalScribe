package com.bolaware.feature_home_domain

import com.bolaware.feature_home_domain.data.LanguageDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Interactor responsible for managing language preferences.
 *
 * This class provides methods to retrieve supported languages, save user-selected languages,
 * and fetch the currently selected language as a Flow.
 *
 * @property settingsManager Handles saving and retrieving user language preferences.
 * @property languagesRepository Provides the list of supported languages.
 */
class LanguageInteractor @Inject constructor(
    private val settingsManager: SettingsManager,
    private val languagesRepository: LanguagesRepository
) {

    /**
     * Retrieves the list of supported languages from the repository.
     *
     * @return A list of [LanguageDomain] objects representing available languages.
     */
    fun getSupportedLanguages(): List<LanguageDomain> {
        return languagesRepository.getSupportedLanguages()
    }

    /**
     * Saves the user's language preference.
     *
     * @param language The [Language] object representing the selected language.
     */
    suspend fun saveLanguageChoice(language: LanguageDomain) {
        settingsManager.saveLanguage(language.key)
    }

    /**
     * Gets the currently selected language as a Flow.
     *
     * This function retrieves the saved language from the [SettingsManager] and maps it
     * to the corresponding [LanguageDomain] object from the supported languages.
     *
     * @return A [Flow] emitting the selected [LanguageDomain].
     * @throws IllegalStateException if the saved language does not match any supported languages.
     */
    fun getSelectedLanguage(): Flow<LanguageDomain> {
        return settingsManager.userLanguage.map { savedLanguage ->
            checkNotNull(getSupportedLanguages().find { it.key == savedLanguage })
        }
    }
}
