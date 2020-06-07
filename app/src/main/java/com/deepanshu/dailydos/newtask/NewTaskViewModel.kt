package com.deepanshu.dailydos.newtask

import android.app.Application
import android.util.Log
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.deepanshu.dailydos.database.DailyTask
import com.deepanshu.dailydos.database.TaskDatabaseDao
import kotlinx.coroutines.*

class NewTaskViewModel(
    val database: TaskDatabaseDao,
    application: Application): AndroidViewModel(application) {

    private val callbacks: PropertyChangeRegistry = PropertyChangeRegistry()

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _task = MutableLiveData<DailyTask>()
    val task: LiveData<DailyTask>
    get() = _task


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
            _task.value?.let {
                it.taskTitle = title
                update(it)
                return@launch
            }

            val newTask = DailyTask(taskTitle = title)
            insert(newTask)
        }

        _navigateToDailyTasks.value = true
    }

    fun getTask(taskId: Long) {
        uiScope.launch {
            _task.value = get(taskId)
        }
    }

    fun updateTask() {
        _task.value?.let {
            uiScope.launch {
                update(it)
            }
        }

    }

    private suspend fun insert(task: DailyTask) {
        withContext(Dispatchers.IO) {
            database.insert(task)
        }
    }

    private suspend fun update(task: DailyTask) {
        withContext(Dispatchers.IO) {
            database.update(task)
        }
    }

    private suspend fun get(taskId: Long): DailyTask? {
        return withContext(Dispatchers.IO) {
            database.get(taskId)
        }
    }

}