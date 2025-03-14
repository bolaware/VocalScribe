package com.bolaware.feature_history.domain

import com.bolaware.data.transcript.Transcript
import com.bolaware.data.transcript.TranscriptRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TranscriptInteractor @Inject constructor(
    private val transcriptRepository: TranscriptRepository
) {

    fun getTranscripts(): Flow<List<Transcript>> {
        return transcriptRepository.getTranscripts()
    }

    suspend fun deleteTranscript(transcript: Transcript) {
        return transcriptRepository.deleteTranscript(transcript)
    }
}