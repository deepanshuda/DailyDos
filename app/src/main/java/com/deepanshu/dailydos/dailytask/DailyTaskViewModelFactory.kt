package com.deepanshu.dailydos.dailytask

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.deepanshu.dailydos.database.TaskDatabaseDao
import java.lang.IllegalArgumentException

class DailyTaskViewModelFactory(
        private val dataSource: TaskDatabaseDao,
        private val application: Application): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DailyTaskViewModel::class.java)) {
            return DailyTaskViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}