package com.example.twinmind_assignment.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.twinmind_assignment.ui.components.LargeMicButton
import com.example.twinmind_assignment.viewmodel.RecorderViewModel

@Composable
fun RecorderScreen(
    viewModel: RecorderViewModel,
    onStopNavigate: () -> Unit,
    onCancel: () -> Unit
) {
    val state = viewModel.uiState.collectAsState().value

    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(state.status)
            Spacer(Modifier.height(24.dp))

            LargeMicButton(
                isRecording = state.isRecording,
                onClick = {
                    if (state.isRecording) {
                        viewModel.stopRecording()
                        onStopNavigate()
                    } else {
                        viewModel.startRecording()
                    }
                }
            )
        }

        Button(onClick = onCancel) {
            Text("Cancel")
        }
    }
}
