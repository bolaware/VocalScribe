package com.bolaware.data.transcript

import com.bolaware.data.database.TranscriptDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TranscriptRepository @Inject constructor(private val dao: TranscriptDao) {
    suspend fun saveTranscript(text: String) {
        val transcript = Transcript(text = text, timestamp = System.currentTimeMillis())
        withContext(Dispatchers.IO) { dao.insertTranscript(transcript) }
    }

    suspend fun getTranscripts(): List<Transcript> {
        return withContext(Dispatchers.IO) { dao.getAllTranscripts() }
    }

    suspend fun clearTranscripts() {
        withContext(Dispatchers.IO) { dao.clearTranscripts() }
    }
}
