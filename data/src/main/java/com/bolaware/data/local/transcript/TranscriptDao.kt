package com.bolaware.data.local.transcript

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TranscriptDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTranscript(transcript: Transcript)

    @Query("SELECT * FROM transcripts ORDER BY timestamp DESC")
    fun getAllTranscripts(): Flow<List<Transcript>>

    @Query("DELETE FROM transcripts WHERE id = :id")
    suspend fun deleteTranscriptById(id: Int)
}
