package com.deepanshu.dailydos.newtask

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.deepanshu.dailydos.database.DailyTask
import com.deepanshu.dailydos.database.TaskDatabaseDao
import kotlinx.coroutines.*

class NewTaskViewModel(
    val database: TaskDatabaseDao,
    application: Application): AndroidViewModel(application) {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private val _navigateToDailyTasks = MutableLiveData<Boolean?>()
    val navigateToDailyTasks: LiveData<Boolean?>
        get() = _navigateToDailyTasks

    fun doneNavigating() {
        _navigateToDailyTasks.value =false
    }

    fun onSaveTask(title: String) {
        Log.i("NewTask", "$title")
        uiScope.launch {
            val newTask = DailyTask(taskTitle = title)
            insert(newTask)
        }

        _navigateToDailyTasks.value = true
    }

    private suspend fun insert(task: DailyTask) {
        withContext(Dispatchers.IO) {
            database.insert(task)
        }
    }

}