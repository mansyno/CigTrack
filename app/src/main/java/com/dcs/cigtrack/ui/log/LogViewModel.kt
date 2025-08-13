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
// import kotlinx.coroutines.flow.map // No longer needed for sorting, handled by DAO
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

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

    fun addLog(remarkId: Int?) {
        viewModelScope.launch {
            val newLogEntry = LogEntry(
                timestamp = System.currentTimeMillis(),
                remarkId = remarkId
            )
            logEntryDao.insert(newLogEntry)
        }
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