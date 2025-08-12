@file:OptIn(ExperimentalMaterial3Api::class)

package com.dcs.cigtrack.ui.remark

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dcs.cigtrack.LogApplication
import com.dcs.cigtrack.ui.theme.CigTrackTheme

@Composable
fun RemarkSettingsScreen(
    viewModel: RemarkSettingsViewModel = viewModel(
        factory = RemarkSettingsViewModel.RemarkSettingsViewModelFactory(
            (LocalContext.current.applicationContext as LogApplication).database.remarkDao()
        )
    )
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Remark Settings") })
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            Text("Remarks List (TODO)")
            Button(onClick = { /* TODO: Add remark */ }) {
                Text("Add Remark")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RemarkSettingsScreenPreview() {
    CigTrackTheme {
        // Preview will not work with real ViewModel needing DAO.
        // Consider creating a fake ViewModel for preview or passing null/fake DAO.
        // For now, removing the ViewModel instantiation for preview to compile.
        // RemarkSettingsScreen()
        Text("Preview of RemarkSettingsScreen (ViewModel logic omitted)")
    }
}
