package com.dcs.cigtrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dcs.cigtrack.ui.log.LogScreen
import com.dcs.cigtrack.ui.log.LogViewModel
import com.dcs.cigtrack.ui.log.LogViewModelFactory
import com.dcs.cigtrack.ui.remark.RemarkSettingsScreen
import com.dcs.cigtrack.ui.theme.CigTrackTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val logApplication = application as LogApplication
        val logEntryDao = logApplication.database.logEntryDao()
        val remarkDao = logApplication.database.remarkDao()
        val logViewModelFactory = LogViewModelFactory(logEntryDao, remarkDao)

        setContent {
            CigTrackTheme {
                var showSettings by remember { mutableStateOf(false) }
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // Apply padding to the content of the Scaffold
                    val contentModifier = Modifier.padding(innerPadding)

                    if (showSettings) {
                        RemarkSettingsScreen(
                            modifier = contentModifier,
                            onNavigateBack = { showSettings = false }
                        )
                    } else {
                        val logViewModel: LogViewModel = viewModel(factory = logViewModelFactory)
                        LogScreen(
                            logViewModel = logViewModel,
                            modifier = contentModifier,
                            onNavigateToSettings = { showSettings = true }
                        )
                    }
                }
            }
        }
    }
}
