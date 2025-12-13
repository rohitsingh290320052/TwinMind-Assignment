package com.example.twinmind_assignment.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.OnConflictStrategy


@Dao
interface MeetingDao {

    @Query("SELECT * FROM meetings ORDER BY createdAt DESC")
    fun observeMeetings(): Flow<List<MeetingEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeeting(meeting: MeetingEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChunk(chunk: TranscriptChunkEntity)

    @Query("""
        SELECT * FROM transcript_chunks
        WHERE meetingId = :meetingId
        ORDER BY chunkIndex ASC
    """)
    suspend fun getOrderedChunks(meetingId: Long): List<TranscriptChunkEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSummary(summary: SummaryEntity)

    @Query("SELECT * FROM summaries WHERE meetingId = :meetingId")
    suspend fun getSummary(meetingId: Long): SummaryEntity?
}
