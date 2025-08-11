package com.dcs.cigtrack.ui.log

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
// import androidx.lifecycle.viewmodel.compose.viewModel // No longer needed for default
import com.dcs.cigtrack.data.LogEntry
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogScreen(
    logViewModel: LogViewModel,
    modifier: Modifier = Modifier
) {
    // Collect state from ViewModel
    val logEntries by logViewModel.logEntries.collectAsState()
    val remarks = logViewModel.remarks

    // State for UI controls
    var selectedRemark by remember { mutableStateOf<String?>(null) }
    var expanded by remember { mutableStateOf(false) }

    // --- FIX: DECLARE THE DATE FORMATTER HERE ---
    val dateFormatter = remember { SimpleDateFormat("MMM dd, yyyy - HH:mm:ss", Locale.getDefault()) }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Dropdown for remarks
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            OutlinedTextField(
                value = selectedRemark ?: "Select Remark",
                onValueChange = {},
                readOnly = true,
                label = { Text("Remark") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .menuAnchor(MenuAnchorType.PrimaryEditable)
                    .fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                remarks.forEach { remark ->
                    DropdownMenuItem(
                        text = { Text(remark) },
                        onClick = {
                            selectedRemark = remark
                            expanded = false
                        }
                    )
                }
                DropdownMenuItem(
                    text = { Text("None") },
                    onClick = {
                        selectedRemark = null
                        expanded = false
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Button to add log
        Button(
            onClick = {
                logViewModel.addLog(selectedRemark)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(horizontal = 16.dp)
        ) {
            Text("Log Cigarette")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // List of log entries
        if (logEntries.isEmpty()) {
            Text(
                text = "No logs yet. Add your first one!",
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                items(logEntries) { entry ->
                    LogEntryItem(entry = entry, dateFormatter = dateFormatter) // Pass the formatter
                    HorizontalDivider()
                }
            }
        }
    }
}

@Composable
fun LogEntryItem(entry: LogEntry, dateFormatter: SimpleDateFormat) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp), // Vertical padding for list items remains
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = entry.remark ?: "No remark",
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = dateFormatter.format(Date(entry.timestamp)), // Use the passed-in formatter
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
