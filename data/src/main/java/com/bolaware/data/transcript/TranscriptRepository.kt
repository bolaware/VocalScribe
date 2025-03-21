package com.bolaware.data.transcript

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
     * @return A [Flow] that emits a list of [Transcript] objects.
     */
    fun getTranscripts(): Flow<List<Transcript>>

    /**
     * Deletes a transcript by its ID.
     *
     * @param transcriptId The ID of the transcript to delete.
     */
    suspend fun deleteTranscript(transcriptId: Int)
}
