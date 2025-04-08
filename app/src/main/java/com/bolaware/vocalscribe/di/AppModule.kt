package com.bolaware.vocalscribe.di

import com.bolaware.core_domain.TranscriptRepository
import com.bolaware.data.languages.LanguagesRepositoryImpl
import com.bolaware.data.preferences.SettingsManagerImpl
import com.bolaware.data.transcript.TranscriptRepositoryImpl
import com.bolaware.feature_home_domain.LanguagesRepository
import com.bolaware.feature_home_domain.SettingsManager
import org.koin.dsl.module

val appModule = module {
    single<SettingsManager> { SettingsManagerImpl(get()) }
    single<LanguagesRepository> { LanguagesRepositoryImpl() }
    single<TranscriptRepository> { TranscriptRepositoryImpl(get()) }
}