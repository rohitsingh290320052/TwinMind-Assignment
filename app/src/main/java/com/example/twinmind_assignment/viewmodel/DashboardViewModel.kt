package com.example.twinmind_assignment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twinmind_assignment.ui.state.DashboardUiState
import com.example.twinmind_assignment.ui.state.SessionUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadSessions()
    }

    private fun loadSessions() {
        viewModelScope.launch {
            _uiState.value = DashboardUiState(
                sessions = listOf(
                    SessionUi(1, "Team Sync", "General discussion", "10:30 AM"),
                    SessionUi(2, "Client Call", "Project progress", "11:15 AM")
                )
            )
        }
    }
}