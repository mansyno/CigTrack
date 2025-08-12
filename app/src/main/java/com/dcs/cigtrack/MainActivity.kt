package com.dcs.cigtrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
        val logViewModelFactory = LogViewModelFactory(logEntryDao)

        setContent {
            CigTrackTheme {
                var showSettings by remember { mutableStateOf(false) }
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        Button(onClick = { showSettings = !showSettings }) {
                            Text(if (showSettings) "Show Log Screen" else "Show Settings Screen")
                        }
                        if (showSettings) {
                            RemarkSettingsScreen()
                        } else {
                            val logViewModel: LogViewModel = viewModel(factory = logViewModelFactory)
                            LogScreen(
                                logViewModel = logViewModel,
                                // Modifier.padding(innerPadding) // This padding is already applied to the Column
                            )
                        }
                    }
                }
            }
        }
    }
}
