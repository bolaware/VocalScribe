package com.bolaware.data.di

import com.bolaware.data.languages.LanguagesRepository
import com.bolaware.data.languages.LanguagesRepositoryImpl
import com.bolaware.data.transcript.TranscriptRepository
import com.bolaware.data.transcript.TranscriptRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class RepositoryModule {
    @Binds
    @ViewModelScoped
    abstract fun bindLanguagesRepository(
        languagesRepository: LanguagesRepositoryImpl
    ): LanguagesRepository

    @Binds
    @ViewModelScoped
    abstract fun bindTranscriptRepository(
        transcriptRepository: TranscriptRepositoryImpl
    ): TranscriptRepository
}