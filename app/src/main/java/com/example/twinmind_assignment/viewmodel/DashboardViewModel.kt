package com.example.twinmind_assignment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twinmind_assignment.data.local.MeetingDao
import com.example.twinmind_assignment.data.local.MeetingEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    meetingDao: MeetingDao
) : ViewModel() {

    val meetings = meetingDao
        .getMeetings()
        .stateIn(
            scope = kotlinx.coroutines.GlobalScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

}
