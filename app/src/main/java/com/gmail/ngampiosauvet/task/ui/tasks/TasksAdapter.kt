package com.gmail.ngampiosauvet.task.ui.tasks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gmail.ngampiosauvet.task.data.Task
import com.gmail.ngampiosauvet.task.databinding.ItemListTaskBinding

class TasksAdapter(private val onClickItem:(Task) -> Unit ):
    ListAdapter<Task, TasksAdapter.TaskViewHolder>(DiffCallback) {



    class TaskViewHolder(private var binding:ItemListTaskBinding ):
        RecyclerView.ViewHolder(binding.root) {
            fun bind(task: Task){

            }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            ItemListTaskBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
      val task = getItem(position)
        holder.itemView.setOnClickListener {
            onClickItem
        }
        holder.bind(task)
    }


    companion object  {
        private val DiffCallback = object : DiffUtil.ItemCallback<Task>() {
            override fun areItemsTheSame(oldTask: Task, newTask: Task): Boolean {
                return oldTask === newTask
            }

            override fun areContentsTheSame(oldTask: Task, newTask: Task): Boolean {
                return oldTask.id == newTask.id
            }
        }

    }

}