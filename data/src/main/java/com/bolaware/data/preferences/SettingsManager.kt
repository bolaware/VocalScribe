package com.bolaware.data.preferences

import kotlinx.coroutines.flow.Flow

interface SettingsManager {
    suspend fun saveLanguage(language: String)

    val userLanguage: Flow<String>
}