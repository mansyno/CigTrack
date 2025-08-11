package com.dcs.cigtrack.ui.log

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.dcs.cigtrack.data.LogEntry
import com.dcs.cigtrack.data.LogEntryDao
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LogViewModel(private val logEntryDao: LogEntryDao) : ViewModel() {

    val remarks: List<String> = listOf("With Coffee", "After Meal", "Waking Up", "Driving", "Stress", "Boredom")

    val logEntries: StateFlow<List<LogEntry>> = logEntryDao.getAllEntries()
        .map { it.sortedByDescending { entry -> entry.timestamp } } // Ensure reverse chronological order as per TDS
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000), // Configured for UI observation
            initialValue = emptyList()
        )

    fun addLog(remark: String?) {
        viewModelScope.launch {
            val newLogEntry = LogEntry(
                timestamp = System.currentTimeMillis(),
                remark = remark
            )
            logEntryDao.insert(newLogEntry)
        }
    }
}

class LogViewModelFactory(private val logEntryDao: LogEntryDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LogViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LogViewModel(logEntryDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}