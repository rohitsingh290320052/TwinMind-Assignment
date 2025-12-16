package com.example.twinmind_assignment.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TranscriptDao {

    @Insert
    suspend fun insertTranscript(transcript: TranscriptEntity)

    @Query("""
        SELECT * FROM transcripts
        WHERE sessionId = :sessionId
        ORDER BY chunkIndex ASC
    """)
    suspend fun getTranscript(sessionId: Long): List<TranscriptEntity>
}
