package com.example.twinmind_assignment.utils

object MimeUtils {
    fun guess(filePath: String): String {
        return when {
            filePath.endsWith(".m4a", ignoreCase = true) -> "audio/mp4"
            filePath.endsWith(".mp3", ignoreCase = true) -> "audio/mpeg"
            filePath.endsWith(".wav", ignoreCase = true) -> "audio/wav"
            filePath.endsWith(".3gp", ignoreCase = true) -> "audio/3gpp"
            else -> "audio/*"
        }
    }
}
