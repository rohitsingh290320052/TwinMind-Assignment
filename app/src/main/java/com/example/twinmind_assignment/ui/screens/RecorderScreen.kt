package com.example.twinmind_assignment.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.twinmind_assignment.viewmodel.RecorderViewModel
import com.example.twinmind_assignment.ui.components.LargeMicButton
import com.example.twinmind_assignment.ui.components.PermissionGate
import com.example.twinmind_assignment.ui.components.PermissionRationale

@Composable
fun RecorderScreen(
    viewModel: RecorderViewModel = hiltViewModel(),
    onStopNavigate: (Long) -> Unit,
    onCancel: () -> Unit
) {
    PermissionGate(
        permission = android.Manifest.permission.RECORD_AUDIO,
        rationale = {
            PermissionRationale(
                onRequest = it,
                onCancel = onCancel
            )
        }
    )
    {

        val state = viewModel.uiState.collectAsState().value

        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Column(
                modifier = Modifier.fillMaxSize().padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Spacer(Modifier.height(8.dp))
                    Text(text = state.status, style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(24.dp))

                    LargeMicButton(
                        isRecording = state.isRecording,
                        onClick = {
                            if (state.isRecording) {
                                viewModel.stopRecording()
                                onStopNavigate(0L)   // Navigate immediately to Processing screen
                            } else {
                                viewModel.startRecording()
                            }
                        }
                    )

                    Spacer(Modifier.height(16.dp))
                    Text(text = state.timer, style = MaterialTheme.typography.headlineMedium)
                }

                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(onClick = onCancel) { Text("Cancel") }

                    Button(onClick = { viewModel.togglePause() }) {
                        Text(if (state.isRecording) "Pause" else "Resume")
                    }
                }
            }
        }
    }
}
