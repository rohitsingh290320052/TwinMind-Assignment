package com.example.twinmind_assignment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twinmind_assignment.domain.SummaryUseCase
import com.example.twinmind_assignment.ui.state.SummaryUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SummaryViewModel @Inject constructor(
    private val summaryUseCase: SummaryUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SummaryUiState())
    val uiState = _uiState.asStateFlow()

    fun generate(text: String) {
        _uiState.value = SummaryUiState(
            loading = true,
            error = null
        )

        viewModelScope.launch {
            try {
                val response = summaryUseCase(text)

                _uiState.value = SummaryUiState(
                    title = response.title,
                    summary = response.summary,
                    keyPoints = response.keyPoints,
                    actionItems = response.actionItems,
                    loading = false
                )

            } catch (e: Exception) {
                _uiState.value = SummaryUiState(
                    error = e.message,
                    loading = false
                )
            }
        }
    }
}
