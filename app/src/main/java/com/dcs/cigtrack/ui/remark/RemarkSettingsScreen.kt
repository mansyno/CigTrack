@file:OptIn(ExperimentalMaterial3Api::class)

package com.dcs.cigtrack.ui.remark

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dcs.cigtrack.LogApplication
import com.dcs.cigtrack.data.Remark // Assuming Remark data class exists
import com.dcs.cigtrack.ui.theme.CigTrackTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RemarkSettingsScreen(
    modifier: Modifier = Modifier, // Added modifier parameter
    viewModel: RemarkSettingsViewModel = viewModel(
        factory = RemarkSettingsViewModel.RemarkSettingsViewModelFactory(
            (LocalContext.current.applicationContext as LogApplication).database.remarkDao()
        )
    ),
    onNavigateBack: () -> Unit // New parameter
) {
    var showDialog by remember { mutableStateOf(false) }
    var newRemarkText by remember { mutableStateOf("") }

    val remarks by viewModel.remarks.collectAsState(initial = emptyList())

    Scaffold(
        modifier = modifier, // Applied modifier
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Navigate back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) { // Main Column for content
            // Header Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Remark",
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    text = "Visible in dropdown?",
                    style = MaterialTheme.typography.titleSmall
                )
            }
            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))

            // List of Remarks
            LazyColumn(
                modifier = Modifier.weight(1f) // LazyColumn takes remaining space
                // Removed contentPadding here as items and header will have their own consistent padding
            ) {
                items(remarks) { remark ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp), // Added horizontal padding to align with header
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp), // Padding inside the card content
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = remark.text,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.weight(1f)
                            )
                            Checkbox(
                                checked = remark.isHidden,
                                onCheckedChange = {
                                    viewModel.updateRemark(remark.copy(isHidden = !remark.isHidden))
                                }
                            )
                        }
                    }
                }
            }

            // Add Remark Button
            Button(
                onClick = { showDialog = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp) // Adjusted padding for consistency
            ) {
                Text("Add Remark")
            }

            // Add Remark Dialog
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
        RemarkSettingsScreen(onNavigateBack = {})
    }
}
