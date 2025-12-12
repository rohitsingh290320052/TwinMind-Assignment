package com.example.twinmind_assignment.data

object TranscriptionCache {
    private val map = mutableMapOf<Long, String>()

    fun save(sessionId: Long, text: String) {
        map[sessionId] = text
    }

    fun get(sessionId: Long): String {
        return map[sessionId] ?: ""
    }
}
