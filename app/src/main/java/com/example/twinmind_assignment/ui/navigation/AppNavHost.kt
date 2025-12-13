package com.example.twinmind_assignment.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.twinmind_assignment.ui.screens.*
import com.example.twinmind_assignment.viewmodel.RecorderViewModel

@Composable
fun AppNavHost() {
    val nav = rememberNavController()

    NavHost(
        navController = nav,
        startDestination = Routes.DASHBOARD
    ) {

        // -----------------------
        // DASHBOARD
        // -----------------------
        composable(Routes.DASHBOARD) {
            DashboardScreen(
                onOpenRecorder = { nav.navigate(Routes.RECORD_FLOW) },
                onOpenSummary = { sessionId ->
                    nav.navigate(Routes.summary(sessionId))
                }
            )
        }

        // -----------------------
        // RECORD FLOW (Nested)
        // -----------------------
        navigation(
            route = Routes.RECORD_FLOW,
            startDestination = Routes.RECORDER
        ) {

            // -----------------------
            // RECORDER
            // -----------------------
            composable(Routes.RECORDER) {

                val parentEntry = remember(nav.currentBackStackEntry) {
                    nav.getBackStackEntry(Routes.RECORD_FLOW)
                }
                val vm: RecorderViewModel = hiltViewModel(parentEntry)

                RecorderScreen(
                    viewModel = vm,
                    onStopNavigate = {
                        nav.navigate(Routes.PROCESSING)
                    },
                    onCancel = {
                        nav.popBackStack()
                    }
                )
            }


            // -----------------------
            // PROCESSING
            // -----------------------
            composable(Routes.PROCESSING) {

                val parentEntry = remember(nav.currentBackStackEntry) {
                    nav.getBackStackEntry(Routes.RECORD_FLOW)
                }
                val vm: RecorderViewModel = hiltViewModel(parentEntry)

                ProcessingScreen(
                    viewModel = vm,
                    onDone = { sessionId ->
                        nav.navigate(Routes.transcript(sessionId))
                    }
                )
            }


            // -----------------------
            // TRANSCRIPT
            // -----------------------
            composable(
                route = Routes.TRANSCRIPT_ROUTE,
                arguments = listOf(
                    navArgument("sessionId") {
                        type = NavType.LongType
                    }
                )
            ) { backStackEntry ->

                val sessionId =
                    backStackEntry.arguments?.getLong("sessionId") ?: -1L

                TranscriptScreen(
                    sessionId = sessionId,
                    onContinue = {
                        nav.navigate(Routes.summary(sessionId))
                    }
                )
            }

            // -----------------------
            // SUMMARY
            // -----------------------
            composable(
                route = Routes.SUMMARY_ROUTE,
                arguments = listOf(
                    navArgument("sessionId") {
                        type = NavType.LongType
                    }
                )
            ) { backStackEntry ->

                val sessionId =
                    backStackEntry.arguments?.getLong("sessionId") ?: -1L

                SummaryScreen(
                    sessionId = sessionId,
                    onBack = {
                        nav.popBackStack()
                    }
                )
            }
        }
    }
}
