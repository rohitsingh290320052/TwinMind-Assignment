package com.example.twinmind_assignment.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.twinmind_assignment.ui.screens.*

@Composable
fun AppNavHost() {
    val nav = rememberNavController()
    NavHost(navController = nav, startDestination = Routes.DASHBOARD) {
        composable(Routes.DASHBOARD) {
            DashboardScreen(
                onOpenRecorder = { nav.navigate(Routes.RECORDER) },
                onOpenSummary = { sessionId -> nav.navigate(Routes.SUMMARY.format(sessionId)) }
            )
        }
        composable(Routes.RECORDER) {
            RecorderScreen(
                onStop = { sessionId ->
                    // navigate to processing screen after stop
                    nav.navigate(Routes.processing(sessionId))
                },
                onCancel = { nav.popBackStack() }
            )
        }
        composable(Routes.PROCESSING) { backStackEntry ->
            val sessionId = backStackEntry.arguments?.getString("sessionId")?.toLongOrNull() ?: -1L
            ProcessingScreen(
                onDone = { /* open transcript or summary when done */ nav.navigate(Routes.transcript(sessionId)) }
            )
        }
        composable(Routes.TRANSCRIPT) { backStackEntry ->
            val sessionId = backStackEntry.arguments?.getString("sessionId")?.toLongOrNull() ?: -1L
            TranscriptScreen(sessionId = sessionId, onContinue = { nav.navigate(Routes.summary(sessionId)) })
        }
        composable(Routes.SUMMARY) { backStackEntry ->
            val sessionId = backStackEntry.arguments?.getString("sessionId")?.toLongOrNull() ?: -1L
            SummaryScreen(sessionId = sessionId, onBack = { nav.popBackStack() })
        }
    }
}
