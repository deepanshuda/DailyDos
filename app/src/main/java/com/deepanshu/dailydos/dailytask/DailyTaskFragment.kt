package com.deepanshu.dailydos.dailytask

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController

import com.deepanshu.dailydos.R
import com.deepanshu.dailydos.database.DailyTask
import com.deepanshu.dailydos.database.TaskDatabase
import com.deepanshu.dailydos.database.TaskDatabaseDao
import com.deepanshu.dailydos.databinding.FragmentDailyTaskBinding

/**
 * A simple [Fragment] subclass.
 */
class DailyTaskFragment : Fragment(), TaskAdapter.OnTaskStatusListener {


    var dataSource: TaskDatabaseDao? = null
    var taskViewModel: DailyTaskViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val binding: FragmentDailyTaskBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_daily_task, container, false)

        binding.newTask.setOnClickListener {
            it.findNavController()
                .navigate(DailyTaskFragmentDirections.actionDailyTaskFragmentToNewTaskFragment())
        }
        val application = requireNotNull(this.activity).application
        dataSource = TaskDatabase.getInstance(application).taskDatabaseDao
        val viewModelFactory = DailyTaskViewModelFactory(dataSource!!, application)
        taskViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(DailyTaskViewModel::class.java)

        val adapter = TaskAdapter()
        taskViewModel!!.tasks.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.tasks = it
            }
        })
        binding.tasksList.adapter = adapter
        adapter.setOnTaskStatusListener(this)

        return binding.root
    }

    override fun onTaskChecked(task: DailyTask) {
//        dataSource!!.update(task)
        taskViewModel!!.updateTask(task)
    }

}
