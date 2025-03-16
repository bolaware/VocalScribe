package com.bolaware.feature_history.di

import com.bolaware.feature_history.timestamp.AndroidTimestampFormatter
import com.bolaware.feature_history.timestamp.TimestampFormatter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object TimestampFormatterModule {

    @Provides
    @ViewModelScoped
    fun provideTimestampFormatter(): TimestampFormatter {
        return AndroidTimestampFormatter()
    }
}
