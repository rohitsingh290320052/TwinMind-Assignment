package com.example.twinmind_assignment.domain

import com.example.twinmind_assignment.domain.model.SummaryResult
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.serialization.json.Json
import javax.inject.Inject

class GenerateSummaryUseCase @Inject constructor(
    private val generativeModel: GenerativeModel
) {

    suspend fun generate(transcript: String): SummaryResult {

        // 🔥 THIS IS WHERE THE PROMPT LIVES
        val prompt = """
            You are an AI assistant that summarizes meeting transcripts.

            Generate a structured summary strictly in JSON with these fields:
            - title
            - summary
            - keyPoints (array of strings)
            - actionItems (array of strings)

            Transcript:
            $transcript
        """.trimIndent()

        val response = generativeModel.generateContent(prompt)

        val jsonText = response.text
            ?: throw IllegalStateException("Empty Gemini response")

        return Json.decodeFromString(jsonText)
    }
}
