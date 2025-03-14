package com.bolaware.data.di

import com.bolaware.data.preferences.SettingsManager
import com.bolaware.data.preferences.SettingsManagerImpl
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