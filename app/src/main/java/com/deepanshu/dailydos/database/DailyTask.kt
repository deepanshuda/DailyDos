package com.deepanshu.dailydos.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "daily_tasks_table")
data class DailyTask(
    @PrimaryKey(autoGenerate = true)
    var taskId: Long = 0L,

    @ColumnInfo(name = "created_date")
    val createdDate: Date = Date(),

    @ColumnInfo(name = "info")
    var taskTitle: String = "",

    @ColumnInfo(name = "task_date")
    var taskDate: Date = createdDate,

    @ColumnInfo(name = "complete")
    var isComplete: Boolean = false
)