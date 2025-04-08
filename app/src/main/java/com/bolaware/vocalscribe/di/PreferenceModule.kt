package com.bolaware.vocalscribe.di

import com.bolaware.data.preferences.SettingsManagerImpl
import com.bolaware.feature_home_domain.SettingsManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class PreferenceModule {
    @Binds
    @Singleton
    abstract fun bindSettingsManager(
        settingsManager: SettingsManagerImpl
    ): SettingsManager
}