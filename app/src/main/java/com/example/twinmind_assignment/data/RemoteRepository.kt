package com.example.twinmind_assignment.data

import android.util.Base64
import com.example.twinmind_assignment.data.local.MeetingDao
import com.example.twinmind_assignment.data.local.MeetingEntity
import com.example.twinmind_assignment.data.local.TranscriptDao
import com.example.twinmind_assignment.data.local.TranscriptEntity
import com.example.twinmind_assignment.domain.model.SummaryResult
import com.example.twinmind_assignment.utils.MimeUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class RemoteRepository @Inject constructor(
    private val api: GeminiApi,
    private val transcriptDao: TranscriptDao,
    private val meetingDao: MeetingDao
) {

    /**
     * Transcribe a single audio file and persist result in Room.
     * This keeps Room as the SINGLE SOURCE OF TRUTH.
     */
    suspend fun transcribeAndSave(
        sessionId: Long,
        filePath: String,
        chunkIndex: Int = 0
    ): String = withContext(Dispatchers.IO) {

        // -------- Read audio --------
        val file = File(filePath)
        val audioBytes = file.readBytes()
        val base64Audio = Base64.encodeToString(audioBytes, Base64.NO_WRAP)
        val mimeType = MimeUtils.guess(filePath)

        // -------- Gemini request --------
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
                        GeminiPart(
                            text = "Transcribe this audio clearly in natural English. Ignore noise."
                        )
                    )
                )
            )
        )

        // -------- API call --------
        val response = api.generateContent(
            GeminiConstants.API_KEY,
            request
        )

        val transcriptionText =
            response.candidates
                ?.firstOrNull()
                ?.content
                ?.parts
                ?.firstOrNull()
                ?.text
                ?: ""

        // -------- Persist to Room (Transcript) --------
        // -------- Persist transcript --------
        transcriptDao.insertTranscript(
            TranscriptEntity(
                sessionId = sessionId,
                chunkIndex = chunkIndex,
                text = transcriptionText
            )
        )

// ✅ SAVE MEETING FOR DASHBOARD
        meetingDao.insertMeeting(
            MeetingEntity(
                sessionId = sessionId,
                createdAt = System.currentTimeMillis(),
                preview = transcriptionText.take(80)
            )
        )

        return@withContext transcriptionText




        return@withContext transcriptionText
    }

    /**
     * Fetch FULL transcript in correct order from Room
     */
    suspend fun getFullTranscript(sessionId: Long): String = withContext(Dispatchers.IO) {

        transcriptDao.getTranscript(sessionId)
            .sortedBy { it.chunkIndex }
            .joinToString(separator = " ") { it.text }
    }

    suspend fun generateSummary(text: String): SummaryResult = withContext(Dispatchers.IO) {

        val request = GeminiRequest(
            contents = listOf(
                GeminiContent(
                    parts = listOf(
                        GeminiPart(
                            text = """
                        Summarize the following meeting transcript into JSON ONLY.

                        Format:
                        {
                          "title": "short title",
                          "summary": "2–3 sentence summary",
                          "keyPoints": ["point 1", "point 2"],
                          "actionItems": ["action 1", "action 2"]
                        }

                        Do NOT add markdown. Do NOT add explanation.
                        """.trimIndent()
                        ),
                        GeminiPart(text = text)
                    )
                )
            )
        )

        val response = api.generateContent(
            GeminiConstants.API_KEY,
            request
        )

        val raw = response.candidates
            ?.firstOrNull()
            ?.content
            ?.parts
            ?.firstOrNull()
            ?.text
            ?: """{"title":"","summary":"","keyPoints":[],"actionItems":[]}"""

        // Clean ```json fences if Gemini adds them
        val cleaned = raw
            .replace("```json", "")
            .replace("```", "")
            .trim()

        kotlinx.serialization.json.Json.decodeFromString(cleaned)
    }

}
