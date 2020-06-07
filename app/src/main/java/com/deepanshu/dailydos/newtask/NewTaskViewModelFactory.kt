package com.deepanshu.dailydos.newtask

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.deepanshu.dailydos.database.TaskDatabaseDao
import java.lang.IllegalArgumentException

class NewTaskViewModelFactory(
    private val dataSource: TaskDatabaseDao,
    private val application: Application): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewTaskViewModel::class.java)) {
            return NewTaskViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown view model class")
    }
}