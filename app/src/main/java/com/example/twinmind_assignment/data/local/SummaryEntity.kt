package com.example.twinmind_assignment.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.json.Json

@Entity(tableName = "summaries")
data class SummaryEntity(
    @PrimaryKey val meetingId: Long,
    val title: String,
    val summary: String,
    val keyPointsJson: String,
    val actionItemsJson: String
) {
    val keyPoints: List<String>
        get() = Json.decodeFromString(keyPointsJson)

    val actionItems: List<String>
        get() = Json.decodeFromString(actionItemsJson)
}
