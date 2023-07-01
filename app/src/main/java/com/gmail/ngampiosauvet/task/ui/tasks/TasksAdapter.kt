package com.gmail.ngampiosauvet.task.ui.tasks

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
//import android.widget.CompoundButton
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gmail.ngampiosauvet.task.data.Task
import com.gmail.ngampiosauvet.task.databinding.ItemListTaskBinding

class TasksAdapter(
    private val onClickCheckbox: (Task) -> CompoundButton.OnCheckedChangeListener,
    private val onClickItemView: (Task) -> Unit,
) : ListAdapter<Task, TasksAdapter.TaskViewHolder>(DiffCallback) {


    class TaskViewHolder(binding: ItemListTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val checkboxTitle = binding.checkboxTitle
        val title = binding.title

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

        holder.checkboxTitle.setOnCheckedChangeListener(null)
        holder.checkboxTitle.isChecked =  task.isCompleted
        holder.checkboxTitle.setOnCheckedChangeListener(onClickCheckbox(task))
        /**{ _, isChecked ->
              task.isCompleted = isChecked
              onClickCheckbox(task)
        }**/



        holder.title.text = task.title
        holder.title.paint.isStrikeThruText = task.isCompleted

        holder.itemView.setOnClickListener {
            onClickItemView(task) }
    }





    companion object {
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