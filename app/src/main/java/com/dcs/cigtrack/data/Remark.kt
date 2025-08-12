package com.dcs.cigtrack.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remarks")
data class Remark(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val text: String
)
