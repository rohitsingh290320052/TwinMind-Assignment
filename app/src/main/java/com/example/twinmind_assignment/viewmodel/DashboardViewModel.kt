package com.example.twinmind_assignment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twinmind_assignment.data.db.MeetingDao
import com.example.twinmind_assignment.ui.state.DashboardUiState
import com.example.twinmind_assignment.ui.state.SessionUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    dao: MeetingDao
) : ViewModel() {

    val uiState = dao.observeMeetings()
        .map { meetings ->
            DashboardUiState(
                sessions = meetings.map {
                    SessionUi(
                        id = it.id,
                        title = it.title,
                        subtitle = it.status,
                        time = it.createdAt.toString()
                    )
                }
            )
        }
        .stateIn(viewModelScope, kotlinx.coroutines.flow.SharingStarted.Lazily, DashboardUiState())
}
