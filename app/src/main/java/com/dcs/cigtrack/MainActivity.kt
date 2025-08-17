package com.dcs.cigtrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dcs.cigtrack.ui.analysis.AnalysisScreen
import com.dcs.cigtrack.ui.log.LogScreen
import com.dcs.cigtrack.ui.log.LogViewModel
import com.dcs.cigtrack.ui.log.LogViewModelFactory
import com.dcs.cigtrack.ui.remark.RemarkSettingsScreen
import com.dcs.cigtrack.ui.theme.CigTrackTheme

@OptIn(ExperimentalMaterial3Api::class)
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
                var showAnalysis by remember { mutableStateOf(false) }
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { Text(if (showSettings) stringResource(R.string.settings_title) else stringResource(R.string.app_name_short)) },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                titleContentColor = MaterialTheme.colorScheme.onPrimary
                            )
                        )
                    }
                ) { innerPadding ->
                    // Apply padding to the content of the Scaffold
                    val contentModifier = Modifier.padding(innerPadding)

                    if (showSettings) {
                        RemarkSettingsScreen(
                            modifier = contentModifier,
                            onNavigateBack = { showSettings = false }
                        )
                    } else if (!showAnalysis) {
                        val logViewModel: LogViewModel = viewModel(factory = logViewModelFactory)
                        LogScreen(
                            logViewModel = logViewModel,
                            modifier = contentModifier,
                            onNavigateToSettings = { showSettings = true },
                            onNavigateToAnalysis = { showAnalysis = true }
                        )
                    } else {
                        AnalysisScreen(
                            modifier = contentModifier,
                            onNavigateBack = { showAnalysis = false }
                        )
                    }
                }
            }
        }
    }
}
