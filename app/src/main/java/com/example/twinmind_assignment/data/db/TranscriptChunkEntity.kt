package com.example.twinmind_assignment.data.db

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "transcript_chunks",
    indices = [Index(value = ["meetingId", "chunkIndex"], unique = true)]
)
data class TranscriptChunkEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val meetingId: Long,
    val chunkIndex: Int,
    val text: String
)
