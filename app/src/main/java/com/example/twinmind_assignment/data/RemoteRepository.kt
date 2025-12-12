package com.example.twinmind_assignment.data

import android.util.Base64
import com.example.twinmind_assignment.domain.model.SummaryResult
import com.example.twinmind_assignment.utils.MimeUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.io.File
import javax.inject.Inject

class RemoteRepository @Inject constructor(
    private val api: GeminiApi
) {

    suspend fun transcribeAudio(filePath: String): String = withContext(Dispatchers.IO) {

        val file = File(filePath)
        val audioBytes = file.readBytes()
        val base64Audio = Base64.encodeToString(audioBytes, Base64.NO_WRAP)
        val mimeType = MimeUtils.guess(filePath)

        val request = GeminiRequest(
            contents = listOf(
                GeminiContent(
                    parts = listOf(
                        GeminiPart(
                            inline_data = InlineData(
                                mime_type = mimeType,
                                data = base64Audio
                            )
                        ),
                        GeminiPart(text = "Transcribe this audio in natural English. Remove noise.")
                    )
                )
            )
        )

        val response = api.generateContent(GeminiConstants.API_KEY, request)

        return@withContext response.candidates
            ?.firstOrNull()
            ?.content
            ?.parts
            ?.firstOrNull()
            ?.text ?: "Transcription failed"
    }



    suspend fun generateSummary(text: String): SummaryResult = withContext(Dispatchers.IO) {

        val request = GeminiRequest(
            contents = listOf(
                GeminiContent(
                    parts = listOf(
                        GeminiPart(
                            text = """
                                Summarize the following text into a JSON object:

                                {
                                  "title": "short heading",
                                  "summary": "2–3 sentence summary",
                                  "keyPoints": ["point 1", "point 2", ...],
                                  "actionItems": ["task 1", "task 2", ...]
                                }

                                Return **ONLY JSON**, no explanation.
                            """.trimIndent()
                        ),
                        GeminiPart(text = text)
                    )
                )
            )
        )

        val response = api.generateContent(GeminiConstants.API_KEY, request)

        val jsonString = response.candidates
            ?.firstOrNull()
            ?.content
            ?.parts
            ?.firstOrNull()
            ?.text ?: """{"title":"","summary":"","keyPoints":[],"actionItems":[]}"""


        val cleaned = jsonString
            .replace("```json", "")
            .replace("```", "")
            .trim()



        val test = """{"title":"hello","summary":"world","keyPoints":["a"],"actionItems":["b"]}"""
        val parsedTest = Json.decodeFromString<SummaryResult>(test)
        println("JSON PARSED OK => $parsedTest")



        return@withContext Json.decodeFromString<SummaryResult>(cleaned)


    }
}
