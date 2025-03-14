package com.bolaware.data.transcript

import com.bolaware.data.database.TranscriptDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TranscriptRepository @Inject constructor(private val dao: TranscriptDao) {

    suspend fun saveTranscript(text: String) {
        val transcript = Transcript(text = text, timestamp = System.currentTimeMillis())
        dao.insertTranscript(transcript)
    }

    fun getTranscripts(): Flow<List<Transcript>> {
        return dao.getAllTranscripts()
    }

    suspend fun deleteTranscript(transcript: Transcript) {
        dao.deleteTranscript(transcript)
    }
}
