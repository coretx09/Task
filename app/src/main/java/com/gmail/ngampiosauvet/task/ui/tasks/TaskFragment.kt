package com.gmail.ngampiosauvet.task.ui.tasks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Toast
import androidx.core.app.ActivityCompat.invalidateOptionsMenu
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.gmail.ngampiosauvet.task.R
import com.gmail.ngampiosauvet.task.data.Task
import com.gmail.ngampiosauvet.task.databinding.FragmentTaskBinding
import com.gmail.ngampiosauvet.task.ui.addTask.AddFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch


class TaskFragment :  Fragment() {

   // private val  task = Task()


    private var _binding: FragmentTaskBinding? =null
    private val binding get() = _binding!!



    private val viewModel:TaskViewModel by viewModels {TaskViewModel.Factory}


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       _binding = FragmentTaskBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val navController = findNavController()
        val drawerLayout = binding.drawerLayout
        val appBarConfiguration= AppBarConfiguration(navController.graph,drawerLayout)
        binding.materialToolbar.setupWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController, )

        binding.floatingActionButton.setOnClickListener {
            val bottomSheet = AddFragment()
            bottomSheet.show(parentFragmentManager, bottomSheet.tag)
        }

        val adapter = TasksAdapter(
            { onClickCheckbox2(it) },
            { onClickTitle(it) }
        )
        binding.recyclerView.adapter = adapter

        val delete = binding.materialToolbar.menu.findItem(R.id.delete)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.tasksUiState.collect() {uiState ->
                    when(uiState) {
                        is TasksUiState.EmptyTask -> {
                            binding.textAllTask.visibility = View.GONE
                            binding.imgLogo.visibility = View.VISIBLE
                            delete.isEnabled = false
                            binding.noTask.visibility = View.VISIBLE
                        }


                        is TasksUiState.Success -> {
                            binding.textAllTask.visibility = View.VISIBLE
                            adapter.submitList(uiState.items)
                            binding.imgLogo.visibility = View.GONE
                            delete.isEnabled = true
                            binding.noTask.visibility = View.GONE
                        }
                    }
                }
            }
        }


        binding.materialToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.delete -> {
                    deleteAllTasksDialog()
                    true
                }
                else -> false
            }
        }

    }

    private fun onClickCheckbox(task: Task){
        val taskId = task.id
        val isCompleted = task.isCompleted
        viewModel.completed(
            taskId,
            isCompleted
        )
       // Toast.makeText(context, "${task.title} completed", Toast.LENGTH_SHORT).show()
    }

    private fun onClickCheckbox2(task: Task) :CompoundButton.OnCheckedChangeListener {

        return CompoundButton.OnCheckedChangeListener{_, isChecked ->
            val taskId = task.id
             viewModel.completed(taskId, isChecked)
        }
    }

    private fun onClickTitle(task: Task){
        val taskId = task.id
        Toast.makeText(context, "GO $taskId ", Toast.LENGTH_SHORT).show()
    }

    private fun deleteAllTasksDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("DELETE TASKS")
            .setMessage("Do you want delete all tasks ?")
            .setCancelable(true)
            .setNegativeButton("No"){_, _ ->}
            .setPositiveButton("Yes") {_, _ -> viewModel.deleteAll()}

            .show()
    }




    private fun render(uiState: TasksUiState){
        when (uiState) {
            is TasksUiState.Success -> {
                binding.textAllTask.visibility = View.VISIBLE
               // adapter.submitList(uiState.items)
                binding.imgLogo.visibility = View.GONE
            }
            TasksUiState.EmptyTask -> {
                binding.textAllTask.visibility = View.GONE
                binding.imgLogo.visibility = View.VISIBLE
            }
        }
    }


}