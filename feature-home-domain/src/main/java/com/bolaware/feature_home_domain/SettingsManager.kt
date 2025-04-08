package com.bolaware.feature_home_domain

import kotlinx.coroutines.flow.Flow

interface SettingsManager {
    suspend fun saveLanguage(language: String)

    val userLanguage: Flow<String>
}