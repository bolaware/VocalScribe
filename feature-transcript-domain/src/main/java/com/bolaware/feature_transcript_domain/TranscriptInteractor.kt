package com.bolaware.feature_transcript_domain

import com.bolaware.core_domain.TranscriptDomain
import com.bolaware.core_domain.TranscriptRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TranscriptInteractor @Inject constructor(
    private val transcriptRepository: TranscriptRepository
) {

    fun getTranscripts(): Flow<List<TranscriptDomain>> {
        return transcriptRepository.getTranscripts()
    }

    suspend fun deleteTranscript(id: Int) {
        return transcriptRepository.deleteTranscript(id)
    }
}