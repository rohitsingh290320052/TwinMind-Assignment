package com.example.twinmind_assignment.domain


import com.example.twinmind_assignment.data.RemoteApi
import javax.inject.Inject

class SummaryUseCase @Inject constructor(
    private val api: RemoteApi
) {
    suspend operator fun invoke(text: String): String {
        return api.generateSummary(text)
    }
}
