package com.dcs.cigtrack.ui.log

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.dcs.cigtrack.data.LogEntry
import com.dcs.cigtrack.data.LogEntryDao
import com.dcs.cigtrack.data.LogEntryWithRemark // Added import
import com.dcs.cigtrack.data.Remark
import com.dcs.cigtrack.data.RemarkDao
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map // Added import for map operator
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat // Added import
import java.util.Date // Added import
import java.util.Locale // Added import

class LogViewModel(private val logEntryDao: LogEntryDao, private val remarkDao: RemarkDao) : ViewModel() {

    val remarks: StateFlow<List<Remark>> = remarkDao.getAll()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Updated to use LogEntryWithRemark and remove client-side sorting
    val logEntries: StateFlow<List<LogEntryWithRemark>> = logEntryDao.getAllEntries()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // New groupedLogEntries StateFlow
    val groupedLogEntries: StateFlow<Map<String, List<LogEntryWithRemark>>> = logEntries.map { entries ->
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        entries
            .groupBy { entryWithRemark ->
                dateFormat.format(Date(entryWithRemark.logEntry.timestamp))
            }
            .toSortedMap(compareByDescending { it }) // Sort keys (dates) in descending order
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyMap()
    )

    fun addLog(remarkId: Int?) {
        viewModelScope.launch {
            val newLogEntry = LogEntry(
                timestamp = System.currentTimeMillis(),
                remarkId = remarkId
            )
            logEntryDao.insert(newLogEntry)
        }
    }

    suspend fun deleteLog(logEntry: LogEntryWithRemark) {
        logEntryDao.delete(logEntry.logEntry)
    }
}

class LogViewModelFactory(private val logEntryDao: LogEntryDao, private val remarkDao: RemarkDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LogViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LogViewModel(logEntryDao, remarkDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}