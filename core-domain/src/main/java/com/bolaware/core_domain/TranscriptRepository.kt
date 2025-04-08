package com.bolaware.core_domain

import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for managing transcript data.
 */
interface TranscriptRepository {

    /**
     * Saves a transcript.
     *
     * @param text The text content of the transcript to be saved.
     */
    suspend fun saveTranscript(text: String)

    /**
     * Retrieves a flow of all saved transcripts.
     *
     * @return A [Flow] that emits a list of [TranscriptDomain] objects.
     */
    fun getTranscripts(): Flow<List<TranscriptDomain>>

    /**
     * Deletes a transcript by its ID.
     *
     * @param transcriptId The ID of the transcript to delete.
     */
    suspend fun deleteTranscript(transcriptId: Int)
}
