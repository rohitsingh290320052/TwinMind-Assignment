package com.example.twinmind_assignment.ui.navigation

import java.net.URLEncoder
import java.net.URLDecoder

object Routes {

    const val DASHBOARD = "dashboard"

    const val RECORD_FLOW = "record_flow"

    const val RECORDER = "recorder"
    const val PROCESSING = "processing"

    // Transcript now takes sessionId + encoded text
    const val TRANSCRIPT_ROUTE = "transcript/{sessionId}/{text}"
    const val SUMMARY_ROUTE = "summary/{sessionId}"

    fun transcript(sessionId: Long, text: String): String {
        val encoded = URLEncoder.encode(text, "UTF-8")
        return "transcript/$sessionId/$encoded"
    }

    fun summary(id: Long) = "summary/$id"

    fun decode(text: String): String {
        return URLDecoder.decode(text, "UTF-8")
    }
}
