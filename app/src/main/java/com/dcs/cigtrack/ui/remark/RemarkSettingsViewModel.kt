package com.dcs.cigtrack.ui.remark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dcs.cigtrack.data.RemarkDao

class RemarkSettingsViewModel(private val remarkDao: RemarkDao) : ViewModel() {
    // TODO: Implement ViewModel logic for managing remarks using remarkDao

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
