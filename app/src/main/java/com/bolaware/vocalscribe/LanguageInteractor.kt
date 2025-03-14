package com.bolaware.vocalscribe

import com.bolaware.data.Language
import com.bolaware.data.languages.LanguagesRepository
import com.bolaware.data.preferences.SettingsManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LanguageInteractor @Inject constructor(
    private val settingsManager: SettingsManager,
    private val languagesRepository: LanguagesRepository
) {

    fun getSupportedLanguages(): List<Language> {
        return languagesRepository.getSupportedLanguages()
    }

    suspend fun saveLanguageChoice(language: Language) {
        settingsManager.saveLanguage(language.key)
    }

    fun getSelectedLanguage(): Flow<Language> {
        return settingsManager.userLanguage.map { savedLanguage ->
            checkNotNull(getSupportedLanguages().find { it.key == savedLanguage } )
        }
    }
}