package com.bolaware.data.local.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.bolaware.data.local.languages.SupportedLanguages
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "user_settings")

class SettingsManagerImpl(
    private val context: Context,
) : com.bolaware.feature_home_domain.SettingsManager {

    companion object {
        private val LANGUAGE_KEY = stringPreferencesKey("user_language")
    }

    override val userLanguage: Flow<String> = context.dataStore.data
        .map { preferences -> preferences[LANGUAGE_KEY] ?: SupportedLanguages.English.language.key }

    override suspend fun saveLanguage(language: String) {
        context.dataStore.edit { preferences ->
            preferences[LANGUAGE_KEY] = language
        }
    }
}