package com.dcs.cigtrack.ui.analysis

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalysisScreen() {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Analysis") })
        }
    ) { paddingValues ->
        // Content padding is available as paddingValues
        Text("Analysis Screen")
    }
}

@Preview(showBackground = true)
@Composable
fun AnalysisScreenPreview() {
    AnalysisScreen()
}
