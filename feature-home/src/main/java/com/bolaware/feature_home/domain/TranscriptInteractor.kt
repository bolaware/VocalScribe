package com.bolaware.feature_home.domain

import com.bolaware.data.transcript.TranscriptRepository
import javax.inject.Inject

class TranscriptInteractor @Inject constructor(
    private val transcriptRepository: TranscriptRepository
) {

    suspend fun saveTranscript(text: String) {
        transcriptRepository.saveTranscript(text)
    }
}