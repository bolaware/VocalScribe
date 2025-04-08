package com.bolaware.speechrecognizer.di

import com.bolaware.speechrecognizer.JsonTextExtractor
import com.bolaware.speechrecognizer.SpeechRecognizer
import com.bolaware.speechrecognizer.SpeechRecognizerImpl
import org.koin.dsl.module

val speechRecognizerModule = module {
    single<SpeechRecognizer> { SpeechRecognizerImpl(get(), JsonTextExtractor()) }
}


