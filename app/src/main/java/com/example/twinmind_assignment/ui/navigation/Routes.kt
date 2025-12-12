package com.example.twinmind_assignment.ui.navigation

object Routes {
    const val DASHBOARD = "dashboard"
    const val RECORDER = "recorder"
    const val PROCESSING = "processing/{sessionId}"
    const val TRANSCRIPT = "transcript/{sessionId}"
    const val SUMMARY = "summary/{sessionId}"

    fun processing(sessionId: Long) = "processing/$sessionId"
    fun transcript(sessionId: Long) = "transcript/$sessionId"
    fun summary(sessionId: Long) = "summary/$sessionId"
}
