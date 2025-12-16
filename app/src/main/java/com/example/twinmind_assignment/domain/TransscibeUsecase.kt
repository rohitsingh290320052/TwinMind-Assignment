package com.example.twinmind_assignment.domain

import com.example.twinmind_assignment.data.RemoteRepository
import javax.inject.Inject

class TranscribeUseCase @Inject constructor(
    private val repository: RemoteRepository
) {
    suspend operator fun invoke(
        sessionId: Long,
        filePath: String
    ): String {
        // NEW unified method
        return repository.transcribeAndSave(sessionId, filePath)
    }
}
