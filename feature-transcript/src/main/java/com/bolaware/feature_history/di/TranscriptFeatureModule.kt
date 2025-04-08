package com.bolaware.feature_history.di

import com.bolaware.core_domain.TranscriptInteractor
import com.bolaware.feature_history.timestamp.AndroidTimestampFormatter
import com.bolaware.feature_history.timestamp.TimestampFormatter
import com.bolaware.feature_history.ui.TranscriptHistoryViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val transcriptFeatureModule = module {
    single<TimestampFormatter> { AndroidTimestampFormatter() }
    viewModel { TranscriptHistoryViewModel(TranscriptInteractor(get()), get()) }
}
