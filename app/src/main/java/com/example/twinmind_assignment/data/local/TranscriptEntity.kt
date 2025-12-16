package com.example.twinmind_assignment.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transcripts") // 🔥 REQUIRED
data class TranscriptEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val sessionId: Long,
    val chunkIndex: Int,
    val text: String
)
