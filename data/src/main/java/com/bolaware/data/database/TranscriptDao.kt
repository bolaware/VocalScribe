package com.bolaware.data.database

import androidx.room.*
import com.bolaware.data.transcript.Transcript
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
