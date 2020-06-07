package com.deepanshu.dailydos.dailytask

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.deepanshu.dailydos.database.DailyTask
import com.deepanshu.dailydos.database.TaskDatabaseDao
import kotlinx.coroutines.*

class DailyTaskViewModel(
                dataSource: TaskDatabaseDao,
                application: Application): AndroidViewModel(application) {

    val database = dataSource

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val tasks = database.getTasks()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun updateTask(task: DailyTask) {
        uiScope.launch {
            update(task)
        }
    }

    private suspend fun update(task: DailyTask) {
        withContext(Dispatchers.IO) {
            database.update(task)
        }
    }


}