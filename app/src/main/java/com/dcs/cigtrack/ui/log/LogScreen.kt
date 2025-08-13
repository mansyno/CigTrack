package com.dcs.cigtrack.ui.log

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dcs.cigtrack.data.LogEntry // Keep for LogEntry itself if needed, or remove if only LogEntryWithRemark is used directly
import com.dcs.cigtrack.data.LogEntryWithRemark // Added import
import com.dcs.cigtrack.data.Remark
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
    val logEntries by logViewModel.logEntries.collectAsState() // Now a List<LogEntryWithRemark>
    val remarksList by logViewModel.remarks.collectAsState()

    var selectedRemark by remember { mutableStateOf<Remark?>(null) }
    var expanded by remember { mutableStateOf(false) }

    val dateFormatter = remember { SimpleDateFormat("MMM dd, yyyy - HH:mm:ss", Locale.getDefault()) }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            OutlinedTextField(
                value = selectedRemark?.text ?: "Select Remark",
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
                remarksList.forEach { remark ->
                    DropdownMenuItem(
                        text = { Text(remark.text) },
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

        Button(
            onClick = {
                logViewModel.addLog(selectedRemark?.id)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(horizontal = 16.dp)
        ) {
            Text("Log Cigarette")
        }

        Spacer(modifier = Modifier.height(16.dp))

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
                items(logEntries) { entryWithRemark -> // entry is now LogEntryWithRemark
                    // Pass the LogEntryWithRemark object to LogEntryItem
                    LogEntryItem(entry = entryWithRemark, dateFormatter = dateFormatter)
                    HorizontalDivider()
                }
            }
        }
    }
}

@Composable
// Updated to accept LogEntryWithRemark
fun LogEntryItem(entry: LogEntryWithRemark, dateFormatter: SimpleDateFormat) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            // Display remark text from the related Remark object
            text = entry.remark?.text ?: "No Remark",
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            // Access timestamp from the embedded LogEntry object
            text = dateFormatter.format(Date(entry.logEntry.timestamp)),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
