package com.example.twinmind_assignment.worker

import android.content.Context
import androidx.work.*
import androidx.work.CoroutineWorker
import com.example.twinmind_assignment.data.RemoteRepository
import com.example.twinmind_assignment.data.db.MeetingDao
import com.example.twinmind_assignment.data.db.SummaryEntity
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class SummaryWorker @AssistedInject constructor(
    @Assisted ctx: Context,
    @Assisted params: WorkerParameters,
    private val repo: RemoteRepository,
    private val dao: MeetingDao
) : CoroutineWorker(ctx, params) {

    override suspend fun doWork(): Result {
        val meetingId = inputData.getLong("meetingId", -1)

        val transcript = dao.getOrderedChunks(meetingId)
            .joinToString(" ") { it.text }

        val result = repo.generateSummary(transcript)

        dao.insertSummary(
            SummaryEntity(
                meetingId = meetingId,
                title = result.title,
                summary = result.summary,
                keyPointsJson = Json.encodeToString(result.keyPoints),
                actionItemsJson = Json.encodeToString(result.actionItems)
            )
        )

        return Result.success()
    }
}
