package com.bolaware.data.transcript

import com.bolaware.data.database.TranscriptDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class TranscriptRepositoryImpl @Inject constructor(
    private val dao: TranscriptDao
): TranscriptRepository {

    override suspend fun saveTranscript(text: String) {
        val transcript = Transcript(text = text, timestamp = System.currentTimeMillis())
        dao.insertTranscript(transcript)
    }

    override fun getTranscripts(): Flow<List<Transcript>> {
        return dao.getAllTranscripts()
    }

    override suspend fun deleteTranscript(transcriptId: Int) {
        dao.deleteTranscriptById(transcriptId)
    }
}
