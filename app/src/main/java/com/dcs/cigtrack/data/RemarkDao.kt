package com.dcs.cigtrack.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface RemarkDao {
    @Query("SELECT * FROM remarks ORDER BY text ASC")
    fun getAll(): Flow<List<Remark>>

    @Insert
    suspend fun insert(remark: Remark)

    @Update
    suspend fun update(remark: Remark)

    @Delete
    suspend fun delete(remark: Remark)
}
