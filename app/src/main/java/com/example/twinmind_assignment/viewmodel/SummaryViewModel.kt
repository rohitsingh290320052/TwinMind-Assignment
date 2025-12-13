package com.example.twinmind_assignment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twinmind_assignment.data.db.MeetingDao
import com.example.twinmind_assignment.domain.GenerateSummaryUseCase
import com.example.twinmind_assignment.ui.state.SummaryUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.json.Json
import javax.inject.Inject
import com.example.twinmind_assignment.domain.model.toEntity


@HiltViewModel
class SummaryViewModel @Inject constructor(
    private val dao: MeetingDao,
    private val generateSummaryUseCase: GenerateSummaryUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SummaryUiState(loading = true))
    val uiState = _uiState.asStateFlow()


    fun loadOrGenerate(meetingId: Long) {
        viewModelScope.launch {
            try {
                // 1️⃣ Load transcript
                val transcript = dao.getOrderedChunks(meetingId)
                    .joinToString(" ") { it.text }

                if (transcript.isBlank()) {
                    _uiState.value = SummaryUiState(
                        loading = false,
                        error = "Transcript is empty"
                    )
                    return@launch
                }

                // 2️⃣ Check cache
                val cached = dao.getSummary(meetingId)
                if (cached != null) {
                    _uiState.value = SummaryUiState.fromEntity(cached)
                    return@launch
                }

                // 3️⃣ Generate summary
                val result = generateSummaryUseCase.generate(transcript)

                // 4️⃣ Save to DB
                dao.insertSummary(result.toEntity(meetingId))

                // 5️⃣ Update UI
                _uiState.value = SummaryUiState(
                    title = result.title,
                    summary = result.summary,
                    keyPoints = result.keyPoints,
                    actionItems = result.actionItems,
                    loading = false
                )

            } catch (e: Exception) {
                e.printStackTrace()
                _uiState.value = SummaryUiState(
                    loading = false,
                    error = "Failed to generate summary"
                )
            }
        }
    }



}
