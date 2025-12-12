package com.example.twinmind_assignment.data

import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

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
}

data class TranscriptionResponse(val text: String)
data class SummaryResponse(val summary: String)
