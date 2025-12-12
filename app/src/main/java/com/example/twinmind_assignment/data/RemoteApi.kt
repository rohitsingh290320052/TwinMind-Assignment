package com.example.twinmind_assignment.data

import okhttp3.MultipartBody
import retrofit2.http.*

interface RemoteApi {

    @Multipart
    @POST("transcribe")
    suspend fun transcribe(
        @Part audio: MultipartBody.Part
    ): TranscriptionResponse

    @POST("summarize")
    suspend fun summarize(
        @Body body: Map<String, String>
    ): SummaryResponse

    suspend fun transcribeAudio(filePath: String): String {
        // TODO: Implement Multipart upload logic here
        return "Dummy transcription text"
    }

    suspend fun generateSummary(text: String): String {
        // TODO: Implement summary API call
        return "Dummy summary text"
    }
}

data class TranscriptionResponse(val text: String)
data class SummaryResponse(val summary: String)
