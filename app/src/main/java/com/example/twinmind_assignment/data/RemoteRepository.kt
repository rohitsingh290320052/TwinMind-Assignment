package com.example.twinmind_assignment.data

import javax.inject.Inject

class RemoteRepository @Inject constructor(
    private val api: RemoteApi
) {

    // MOCK transcription (for assignment demo)
    suspend fun transcribeAudio(filePath: String): String {
        // You can replace this with actual multipart upload later
        return "Dummy transcription for file: $filePath"
    }

    // MOCK LLM summary generation
    suspend fun generateSummary(text: String): String {
        return "Dummy summary generated for text: $text"
    }
}
