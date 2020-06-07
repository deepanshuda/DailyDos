package com.deepanshu.dailydos.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TaskDatabaseDao {

    @Insert
    fun insert(task: DailyTask)

    @Update
    fun update(task: DailyTask)

    @Query("Select * from daily_tasks_table where taskId = :key")
    fun get(key: Long): DailyTask?

    @Query("Select * from daily_tasks_table order by task_date desc")
    fun getTasks(): LiveData<List<DailyTask>>

}