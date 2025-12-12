package com.example.twinmind_assignment.domain


import com.example.twinmind_assignment.data.RemoteApi
import javax.inject.Inject

class TranscribeUseCase @Inject constructor(
    private val api: RemoteApi
) {
    suspend operator fun invoke(filePath: String): String {
        return api.transcribeAudio(filePath)
    }
}
