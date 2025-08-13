package com.dcs.cigtrack.ui.remark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.dcs.cigtrack.data.Remark
import com.dcs.cigtrack.data.RemarkDao
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RemarkSettingsViewModel(private val remarkDao: RemarkDao) : ViewModel() {

    val remarks: StateFlow<List<Remark>> = remarkDao.getAll()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addRemark(remarkText: String) {
        viewModelScope.launch {
            remarkDao.insert(Remark(text = remarkText))
        }
    }

    class RemarkSettingsViewModelFactory(private val remarkDao: RemarkDao) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RemarkSettingsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return RemarkSettingsViewModel(remarkDao) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
