package com.deepanshu.dailydos.dailytask

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.deepanshu.dailydos.R
import com.deepanshu.dailydos.database.DailyTask
import com.deepanshu.dailydos.databinding.ListTaskItemBinding

class TaskAdapter: RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    interface OnTaskStatusListener {
        fun onTaskChecked(task: DailyTask)
    }

    private lateinit var callback: OnTaskStatusListener

    fun setOnTaskStatusListener(callback: OnTaskStatusListener) {
        this.callback = callback
    }

    var tasks = listOf<DailyTask>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun getItemCount() = tasks.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = tasks[position]

        holder.taskTitle.text = task.taskTitle
        holder.taskStatus.isChecked = task.isComplete
        holder.taskStatus.setOnClickListener {
            task.isComplete =!task.isComplete
            callback.onTaskChecked(task)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListTaskItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    class ViewHolder(binding: ListTaskItemBinding): RecyclerView.ViewHolder(binding.root) {
        val taskTitle: TextView = binding.taskTitle
        val taskStatus: RadioButton = binding.taskStatus
    }
}