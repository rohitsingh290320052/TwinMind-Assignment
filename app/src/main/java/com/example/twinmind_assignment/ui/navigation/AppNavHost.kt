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

    NavHost(navController = nav, startDestination = Routes.DASHBOARD) {

        composable(Routes.DASHBOARD) {
            DashboardScreen(
                onOpenRecorder = { nav.navigate(Routes.RECORD_FLOW) },
                onOpenSummary = { sessionId -> nav.navigate(Routes.summary(sessionId)) }
            )
        }

        navigation(route = Routes.RECORD_FLOW, startDestination = Routes.RECORDER) {


            // RECORDER SCREEN

            composable(Routes.RECORDER) {

                // FIX → remember BackStackEntry
                val parentEntry = remember { nav.getBackStackEntry(Routes.RECORD_FLOW) }
                val vm: RecorderViewModel = hiltViewModel(parentEntry)

                RecorderScreen(
                    viewModel = vm,
                    onStopNavigate = { nav.navigate(Routes.PROCESSING) },
                    onCancel = { nav.popBackStack() }
                )
            }


            // PROCESSING SCREEN

            composable(Routes.PROCESSING) {


                val parentEntry = remember { nav.getBackStackEntry(Routes.RECORD_FLOW) }
                val vm: RecorderViewModel = hiltViewModel(parentEntry)

                ProcessingScreen(
                    viewModel = vm,
                    onDone = { sessionId, text ->
                        nav.navigate(Routes.transcript(sessionId, text))
                    }
                )
            }


            // TRANSCRIPT SCREEN

            composable(
                route = Routes.TRANSCRIPT_ROUTE,
                arguments = listOf(
                    navArgument("sessionId") { type = NavType.LongType },
                    navArgument("text") { type = NavType.StringType }
                )
            ) { backStack ->

                val sessionId = backStack.arguments?.getLong("sessionId") ?: -1L
                val textArg = backStack.arguments?.getString("text")

                TranscriptScreen(
                    sessionId = sessionId,
                    textArg = textArg,
                    onContinue = { nav.navigate(Routes.summary(sessionId)) }
                )
            }


            // SUMMARY SCREEN

            composable(Routes.SUMMARY_ROUTE) { backStack ->
                val sessionId = backStack.arguments?.getString("sessionId")?.toLongOrNull() ?: -1L

                SummaryScreen(
                    sessionId = sessionId,
                    onBack = { nav.popBackStack() }
                )
            }
        }
    }
}
