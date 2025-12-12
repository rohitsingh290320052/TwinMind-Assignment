package com.example.twinmind_assignment.domain

import com.example.twinmind_assignment.data.RemoteRepository
import javax.inject.Inject

class TranscribeUseCase @Inject constructor(
    private val repo: RemoteRepository
) {
    suspend operator fun invoke(filePath: String): String {
        return repo.transcribeAudio(filePath)
    }
}
