@file:OptIn(ExperimentalMaterial3Api::class)

package com.dcs.cigtrack.ui.remark

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dcs.cigtrack.LogApplication
import com.dcs.cigtrack.data.Remark // Assuming Remark data class exists
import com.dcs.cigtrack.ui.theme.CigTrackTheme

@Composable
fun RemarkSettingsScreen(
    viewModel: RemarkSettingsViewModel = viewModel(
        factory = RemarkSettingsViewModel.RemarkSettingsViewModelFactory(
            (LocalContext.current.applicationContext as LogApplication).database.remarkDao()
        )
    )
) {
    var showDialog by remember { mutableStateOf(false) }
    var newRemarkText by remember { mutableStateOf("") }

    val remarks by viewModel.remarks.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Remark Settings") })
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(remarks) { remark ->
                    Text(
                        text = remark.text, // Assuming Remark has a 'text' property
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { /* TODO: Implement edit remark functionality */ }
                            .padding(16.dp)
                    )
                }
            }
            Button(
                onClick = { showDialog = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text("Add Remark")
            }

            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text("Add New Remark") },
                    text = {
                        TextField(
                            value = newRemarkText,
                            onValueChange = { newRemarkText = it },
                            label = { Text("Remark text") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                if (newRemarkText.isNotBlank()) {
                                    viewModel.addRemark(newRemarkText)
                                    newRemarkText = "" // Reset text field
                                }
                                showDialog = false
                            }
                        ) {
                            Text("Add")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDialog = false }) {
                            Text("Cancel")
                        }
                    }
                )
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
        Text("Preview of RemarkSettingsScreen (ViewModel logic omitted)")
    }
}
