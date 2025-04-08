package com.bolaware.feature_home_domain

import com.bolaware.core_domain.TranscriptRepository
import javax.inject.Inject

class TranscriptInteractor @Inject constructor(
    private val transcriptRepository: TranscriptRepository
) {

    suspend fun saveTranscript(text: String) {
        transcriptRepository.saveTranscript(text)
    }
}