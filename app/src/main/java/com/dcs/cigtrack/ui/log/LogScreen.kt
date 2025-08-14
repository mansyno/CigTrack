package com.dcs.cigtrack.ui.log

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dcs.cigtrack.data.LogEntryWithRemark
import com.dcs.cigtrack.data.Remark
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun LogScreen(
    logViewModel: LogViewModel,
    modifier: Modifier = Modifier,
    onNavigateToSettings: () -> Unit // New parameter
) {
    val groupedLogEntries by logViewModel.groupedLogEntries.collectAsState()
    val remarksList by logViewModel.remarks.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    // State for the "Add Log" dialog
    var showAddLogDialog by remember { mutableStateOf(false) }
    var selectedRemarkForNewLog by remember { mutableStateOf<Remark?>(null) }
    var remarkDropdownExpanded by remember { mutableStateOf(false) }

    // State for the "Delete Log" dialog
    var showDeleteDialog by remember { mutableStateOf(false) }
    var entryToDelete by remember { mutableStateOf<LogEntryWithRemark?>(null) }
    
    // Date formatter for individual log item times (HH:mm:ss)
    val timeFormatter = remember { SimpleDateFormat("HH:mm:ss", Locale.getDefault()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "Logs", 
                        modifier = Modifier.fillMaxWidth(), 
                        textAlign = TextAlign.Center
                    ) 
                },
                actions = {
                    IconButton(onClick = onNavigateToSettings) { // Updated onClick
                        Icon(Icons.Filled.Settings, contentDescription = "Settings")
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("Log") },
                icon = { Icon(Icons.Filled.Add, contentDescription = "Log new entry") },
                onClick = { showAddLogDialog = true },
                modifier = Modifier.padding(16.dp) // Ensure FAB doesn't overlap status bar
            )
        },
        floatingActionButtonPosition = FabPosition.End, // MODIFIED HERE
        modifier = modifier
    ) { paddingValues ->

        // "Add Log" Dialog
        if (showAddLogDialog) {
            AlertDialog(
                onDismissRequest = { showAddLogDialog = false },
                title = { Text("Log New Entry") },
                text = {
                    ExposedDropdownMenuBox(
                        expanded = remarkDropdownExpanded,
                        onExpandedChange = { remarkDropdownExpanded = !remarkDropdownExpanded },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = selectedRemarkForNewLog?.text ?: "Select Remark (Optional)",
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Remark") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = remarkDropdownExpanded) },
                            modifier = Modifier
                                .menuAnchor(MenuAnchorType.PrimaryEditable) // Corrected Anchor Type
                                .fillMaxWidth()
                        )
                        ExposedDropdownMenu(
                            expanded = remarkDropdownExpanded,
                            onDismissRequest = { remarkDropdownExpanded = false }
                        ) {
                            remarksList.forEach { remark ->
                                DropdownMenuItem(
                                    text = { Text(remark.text) },
                                    onClick = {
                                        selectedRemarkForNewLog = remark
                                        remarkDropdownExpanded = false
                                    }
                                )
                            }
                            DropdownMenuItem(
                                text = { Text("None") },
                                onClick = {
                                    selectedRemarkForNewLog = null
                                    remarkDropdownExpanded = false
                                }
                            )
                        }
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            logViewModel.addLog(selectedRemarkForNewLog?.id)
                            showAddLogDialog = false
                            selectedRemarkForNewLog = null // Reset for next time
                        }
                    ) {
                        Text("Confirm")
                    }
                },
                dismissButton = {
                    Button(onClick = { showAddLogDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }

        // "Delete Log" Dialog
        if (showDeleteDialog && entryToDelete != null) {
            AlertDialog(
                onDismissRequest = { showDeleteDialog = false },
                title = { Text("Delete Log Entry") },
                text = { Text("Are you sure you want to delete this log entry?") },
                confirmButton = {
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                entryToDelete?.let { logViewModel.deleteLog(it) }
                                showDeleteDialog = false
                                entryToDelete = null
                            }
                        }
                    ) {
                        Text("Confirm")
                    }
                },
                dismissButton = {
                    Button(onClick = { showDeleteDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }

        if (groupedLogEntries.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues) // Apply padding from Scaffold
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("No logs yet. Add your first one!")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues), // Apply padding from Scaffold
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                groupedLogEntries.forEach { (dateString, entriesOnDate) ->
                    stickyHeader {
                        Surface(
                            modifier = Modifier.fillParentMaxWidth(),
                            shadowElevation = 4.dp // Add some elevation to distinguish headers
                        ) {
                            Text(
                                text = logViewModel.formatDateString(dateString), // MODIFIED
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp, horizontal = 16.dp) // Keep horizontal padding consistent
                            )
                        }
                    }
                    items(entriesOnDate, key = { it.logEntry.id }) { entryWithRemark ->
                        LogItemCard(
                            entry = entryWithRemark,
                            timeFormatter = timeFormatter,
                            onLongClick = {
                                entryToDelete = entryWithRemark
                                showDeleteDialog = true
                            }
                        )
                        HorizontalDivider()
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LogItemCard(
    entry: LogEntryWithRemark,
    timeFormatter: SimpleDateFormat,
    onLongClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .combinedClickable(
                onClick = { /* No action on simple click for now */ },
                onLongClick = onLongClick
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp), // MODIFIED
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = entry.remark?.text ?: "No Remark",
                style = MaterialTheme.typography.bodyMedium, // MODIFIED
                modifier = Modifier.weight(1f) // Give remark text more space
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = timeFormatter.format(Date(entry.logEntry.timestamp)),
                style = MaterialTheme.typography.bodyLarge // MODIFIED
            )
        }
    }
}
