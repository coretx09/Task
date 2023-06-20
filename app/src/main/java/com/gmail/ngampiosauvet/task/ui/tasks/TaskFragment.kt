package com.gmail.ngampiosauvet.task.ui.tasks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.gmail.ngampiosauvet.task.TaskApplication
import com.gmail.ngampiosauvet.task.data.TaskRepository
import com.gmail.ngampiosauvet.task.databinding.FragmentTaskBinding
import com.gmail.ngampiosauvet.task.ui.AddEditFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class TaskFragment :  Fragment() {


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
            val bottomSheet = AddEditFragment()

            bottomSheet.show(parentFragmentManager, bottomSheet.tag)
        }

        val adapter = TasksAdapter{}
        binding.recyclerView.adapter =adapter

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.tasksUiState.collect() {uiState ->
                    when(uiState) {
                        is TasksUiState.Success -> {
                            adapter.submitList(uiState.items)
                            binding.imgLogo.visibility = View.GONE
                        }
                        is TasksUiState.EmptyTask -> binding.textAllTask.visibility = View.GONE
                    }
                }
            }
        }



    }


}