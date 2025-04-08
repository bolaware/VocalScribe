package com.bolaware.feature_home.di

import com.bolaware.core_domain.TranscriptInteractor
import com.bolaware.feature_home.ui.HomeViewModel
import com.bolaware.feature_home_domain.LanguageInteractor
import com.bolaware.feature_home_domain.SpeechConcatenator
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val homeFeatureModule = module {
    viewModel {
        HomeViewModel(
            get(),
            SpeechConcatenator(),
            LanguageInteractor(get(), get()),
            TranscriptInteractor(get())
        )
    }
}