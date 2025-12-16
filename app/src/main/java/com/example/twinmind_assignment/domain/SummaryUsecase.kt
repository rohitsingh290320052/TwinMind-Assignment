package com.example.twinmind_assignment.domain

import com.example.twinmind_assignment.data.RemoteRepository
import com.example.twinmind_assignment.domain.model.SummaryResult
import javax.inject.Inject

class SummaryUseCase @Inject constructor(
    private val repository: RemoteRepository
) {
    suspend operator fun invoke(text: String): SummaryResult {
        return repository.generateSummary(text)
    }
}
