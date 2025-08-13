package com.dcs.cigtrack.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Embedded
import androidx.room.Relation
import kotlinx.coroutines.flow.Flow

data class LogEntryWithRemark(
    @Embedded val logEntry: LogEntry,
    @Relation(
        parentColumn = "remarkId",
        entityColumn = "id"
    )
    val remark: Remark?
)

@Dao
interface LogEntryDao {
    @Insert
    suspend fun insert(logEntry: LogEntry)

    @Transaction
    @Query("SELECT * FROM log_entries ORDER BY timestamp DESC")
    fun getAllEntries(): Flow<List<LogEntryWithRemark>>
}
