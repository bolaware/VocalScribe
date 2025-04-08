package com.bolaware.core_domain

import kotlinx.coroutines.flow.Flow

class TranscriptInteractor constructor(
    private val transcriptRepository: TranscriptRepository
) {

    suspend fun saveTranscript(text: String) {
        transcriptRepository.saveTranscript(text)
    }

    fun getTranscripts(): Flow<List<TranscriptDomain>> {
        return transcriptRepository.getTranscripts()
    }

    suspend fun deleteTranscript(id: Int) {
        return transcriptRepository.deleteTranscript(id)
    }
}