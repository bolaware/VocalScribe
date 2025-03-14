package com.bolaware.data.database

import androidx.room.*
import com.bolaware.data.transcript.Transcript

@Dao
interface TranscriptDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTranscript(transcript: Transcript)

    @Query("SELECT * FROM transcripts ORDER BY timestamp DESC")
    fun getAllTranscripts(): List<Transcript>

    @Query("DELETE FROM transcripts")
    suspend fun clearTranscripts()
}
