package com.dcs.cigtrack

import android.app.Application
import com.dcs.cigtrack.data.AppDatabase

class LogApplication : Application() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
}
