package com.example.twinmind_assignment.ui.components

import android.Manifest
import android.content.pm.PackageManager
import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.google.accompanist.permissions.*

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionGate(
    permission: String = Manifest.permission.RECORD_AUDIO,
    rationale: @Composable ((onRequest: () -> Unit) -> Unit),
    content: @Composable () -> Unit
) {
    val permissionState = rememberPermissionState(permission)
    val context = LocalContext.current

    when {
        permissionState.status.isGranted -> content()

        permissionState.status.shouldShowRationale ->
            rationale { permissionState.launchPermissionRequest() }

        else ->
            rationale { permissionState.launchPermissionRequest() }
    }
}
