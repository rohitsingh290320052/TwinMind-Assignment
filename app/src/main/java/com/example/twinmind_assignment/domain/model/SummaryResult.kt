package com.example.twinmind_assignment.domain.model

import com.example.twinmind_assignment.data.db.SummaryEntity
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class SummaryResult(
    val title: String,
    val summary: String,
    val keyPoints: List<String>,
    val actionItems: List<String>
)

// 🔁 Domain → DB
fun SummaryResult.toEntity(meetingId: Long): SummaryEntity {
    return SummaryEntity(
        meetingId = meetingId,
        title = title,
        summary = summary,
        keyPointsJson = Json.encodeToString(keyPoints),
        actionItemsJson = Json.encodeToString(actionItems)
    )
}
