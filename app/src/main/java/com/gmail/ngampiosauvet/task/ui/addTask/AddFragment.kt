package com.gmail.ngampiosauvet.task.ui.addTask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.gmail.ngampiosauvet.task.databinding.FragmentAddBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddFragment : BottomSheetDialogFragment()  {


    private var _binding: FragmentAddBinding? =null
    private val binding get() = _binding!!

    private val viewModel: AddEditTaskViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button2.setOnClickListener {
            addNewTask()
        }
    }

    private fun addNewTask(){
        if (isEntryValid()) {
            viewModel.newTask(
                binding.editTitle.text.toString(),
                binding.editDescription.text.toString()
            )
            Toast.makeText(context, "Task Added", Toast.LENGTH_SHORT).show()
            dismiss()
        }
        else Toast.makeText(context, "Add Title  ", Toast.LENGTH_SHORT).show()
    }
    private fun isEntryValid(): Boolean{
        return viewModel.isEntryValid(
            binding.editTitle.text.toString(),
        )
    }



    companion object {
        const val TAG = "ModalBottomSheet"
    }


}