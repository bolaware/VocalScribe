package com.bolaware.data.repository

import com.bolaware.core_domain.TranscriptRepository
import com.bolaware.data.local.transcript.Transcript
import com.bolaware.data.local.transcript.TranscriptDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TranscriptRepositoryImpl(
    private val dao: TranscriptDao
): TranscriptRepository {

    override suspend fun saveTranscript(text: String) {
        val transcript = Transcript(text = text, timestamp = System.currentTimeMillis())
        dao.insertTranscript(transcript)
    }

    override fun getTranscripts(): Flow<List<com.bolaware.core_domain.TranscriptDomain>> {
        return dao.getAllTranscripts().map { transcripts -> transcripts.map { it.toDomain() } }
    }

    override suspend fun deleteTranscript(transcriptId: Int) {
        dao.deleteTranscriptById(transcriptId)
    }
}

fun Transcript.toDomain(): com.bolaware.core_domain.TranscriptDomain {
    return com.bolaware.core_domain.TranscriptDomain(
        id = id,
        text = text,
        timestamp = timestamp
    )
}
