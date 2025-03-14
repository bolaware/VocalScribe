package com.bolaware.feature_history.di

import com.bolaware.feature_history.timestamp.AndroidTimestampFormatter
import com.bolaware.feature_history.timestamp.TimestampFormatter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FormatterModule {

    @Provides
    @Singleton
    fun provideTimestampFormatter(): TimestampFormatter {
        return AndroidTimestampFormatter()
    }
}
