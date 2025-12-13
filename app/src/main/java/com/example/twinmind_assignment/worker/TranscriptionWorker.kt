package com.example.twinmind_assignment.worker

import android.content.Context
import androidx.work.*
import com.example.twinmind_assignment.data.RemoteRepository
import com.example.twinmind_assignment.data.db.MeetingDao
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

class TranscriptionWorker @AssistedInject constructor(
    @Assisted ctx: Context,
    @Assisted params: WorkerParameters,
    private val repo: RemoteRepository,
    private val dao: MeetingDao
) : CoroutineWorker(ctx, params) {

    override suspend fun doWork(): Result {
        val meetingId = inputData.getLong("meetingId", -1)
        return try {
            // placeholder retry logic
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
