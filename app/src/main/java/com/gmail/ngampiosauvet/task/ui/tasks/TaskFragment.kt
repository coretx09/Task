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
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.gmail.ngampiosauvet.task.R
import com.gmail.ngampiosauvet.task.data.Task
import com.gmail.ngampiosauvet.task.databinding.FragmentTaskBinding
import com.gmail.ngampiosauvet.task.ui.addTask.AddFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TaskFragment :  Fragment() {

    private var _binding: FragmentTaskBinding? =null
    private val binding get() = _binding!!

    private val viewModel:TaskViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       _binding = FragmentTaskBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // APP BAR CONFIGURATIONS
        val navController = findNavController()
        val drawerLayout = binding.drawerLayout
        val appBarConfiguration= AppBarConfiguration(navController.graph,drawerLayout)
        binding.materialToolbar.setupWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController, )

        // FLOATING BUTTON
        binding.floatingActionButton.setOnClickListener {
            val bottomSheet = AddFragment()
            bottomSheet.show(parentFragmentManager, bottomSheet.tag)
        }

        // ADAPTER / RECYCLERVIEW
        val adapter = TasksAdapter(
            { onClickCheckbox2(it) },
            { onClickTitle(it) }
        )
        binding.recyclerView.adapter = adapter

        // ICON - DELETE ALL ITEMS  (APP BAR)
        val delete = binding.materialToolbar.menu.findItem(R.id.delete)


        // COLLECT UI STATE AND RENDER
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


        // ACTION - DELETE ALL ITEMS ICON
        binding.materialToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.delete -> {
                    deleteAllTasksDialog()
                    true
                }
                else -> false
            }
        }


        // SWIPE ITEM FOR DELETE
        val itemTouchHelperCallback = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val task = adapter.currentList[position]
                viewModel.deleteItemTask(task)
            }
        } )
        itemTouchHelperCallback.attachToRecyclerView(binding.recyclerView)

    }







    //                    MY FUNCTIONS AND RENDERS

    // ACTION - CLICK CHECKBOX
    private fun onClickCheckbox2(task: Task) :CompoundButton.OnCheckedChangeListener {
        return CompoundButton.OnCheckedChangeListener{_, isChecked ->
            val taskId = task.id
             viewModel.completed(taskId, isChecked)
        }
    }

    // ACTION - CLICK TITLE
    private fun onClickTitle(task: Task){
        val taskId = task.id
        Toast.makeText(context, "GO $taskId ", Toast.LENGTH_SHORT).show()
    }


    // DIALOG - DELETE ALL ITEMS
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