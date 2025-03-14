package com.bolaware.speechrecognizer.di

import com.bolaware.speechrecognizer.SpeechRecognizer
import com.bolaware.speechrecognizer.SpeechRecognizerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class SpeechRecognizerModule {

    @Binds
    @Singleton
    abstract fun bindSpeechRecognizer(
        speechRecognizerImpl: SpeechRecognizerImpl
    ): SpeechRecognizer
}