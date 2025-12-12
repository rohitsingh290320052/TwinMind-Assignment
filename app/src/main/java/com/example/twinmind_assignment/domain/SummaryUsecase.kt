package com.example.twinmind_assignment.domain

import com.example.twinmind_assignment.data.RemoteRepository
import javax.inject.Inject

class SummaryUseCase @Inject constructor(
    private val repo: RemoteRepository
) {
    suspend operator fun invoke(text: String): String {
        return repo.generateSummary(text)
    }
}
