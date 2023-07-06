package com.gmail.ngampiosauvet.task.ui.taskDetail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.gmail.ngampiosauvet.task.R
import com.gmail.ngampiosauvet.task.data.Task
import com.gmail.ngampiosauvet.task.databinding.FragmentTaskDetailBinding
import com.gmail.ngampiosauvet.task.ui.addTask.AddEditTaskViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

private const val TAG = "TaskFragment"

@AndroidEntryPoint
class TaskDetailFragment : Fragment() {


    private var _binding: FragmentTaskDetailBinding? = null
    private val binding get() = _binding!!

    private val navArgs : TaskDetailFragmentArgs by navArgs()
    private val viewModel: AddEditTaskViewModel by activityViewModels()
    lateinit var task: Task

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTaskDetailBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val appBarConfiguration = AppBarConfiguration(findNavController().graph, )
        binding.materialToolbar.setupWithNavController(findNavController(), appBarConfiguration)


        val taskId = navArgs.taskId
        viewModel.retrieveTaskById(taskId)
        Log.d(TAG, "LOADING")

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){

                viewModel.addEditTaskUiState.collect{ taskUiState->


                    Log.d(TAG, "collecting")
                    if (taskUiState.isTaskOpen) {
                        task = taskUiState.task
                        Log.d(TAG, "collecting ${task.title}")
                        binding.editTitle.setText(task.title)
                        binding.editDescription.setText(task.description)
                        Log.d(TAG, "bind edit ${task.title}")
                    }

                }
            }
        }
    }


}