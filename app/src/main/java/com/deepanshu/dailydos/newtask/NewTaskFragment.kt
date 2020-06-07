package com.deepanshu.dailydos.newtask

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.deepanshu.dailydos.R
import com.deepanshu.dailydos.database.TaskDatabase
import com.deepanshu.dailydos.databinding.FragmentNewTaskBinding

/**
 * A simple [Fragment] subclass.
 */
class NewTaskFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentNewTaskBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_new_task, container, false)
        binding.saveTask.setOnClickListener {
            it.findNavController()
                .navigate(NewTaskFragmentDirections.actionNewTaskFragmentToDailyTaskFragment())
        }

        val application = requireNotNull(this.activity).application
        val dataSource = TaskDatabase.getInstance(application).taskDatabaseDao
        val viewModelFactory = NewTaskViewModelFactory(dataSource, application)
        val newTaskViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(NewTaskViewModel::class.java)
        binding.newTaskViewModel = newTaskViewModel

        newTaskViewModel.navigateToDailyTasks.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                this.findNavController().navigate(NewTaskFragmentDirections.actionNewTaskFragmentToDailyTaskFragment())
                newTaskViewModel.doneNavigating()
                val inputMethodManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(binding.saveTask.windowToken, 0)
            }
        })

        return binding.root
    }
}
