package com.dcs.cigtrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dcs.cigtrack.ui.log.LogScreen
import com.dcs.cigtrack.ui.log.LogViewModel
import com.dcs.cigtrack.ui.log.LogViewModelFactory
import com.dcs.cigtrack.ui.theme.CigTrackTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val logApplication = application as LogApplication
        val logEntryDao = logApplication.database.logEntryDao()
        val logViewModelFactory = LogViewModelFactory(logEntryDao)

        setContent {
            CigTrackTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val logViewModel: LogViewModel = viewModel(factory = logViewModelFactory)
                    LogScreen(
                        logViewModel = logViewModel,
                        modifier = Modifier.padding(innerPadding) // Pass innerPadding to LogScreen
                    )
                }
            }
        }
    }
}
