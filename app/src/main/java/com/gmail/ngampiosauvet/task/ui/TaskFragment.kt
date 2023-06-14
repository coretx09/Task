package com.gmail.ngampiosauvet.task.ui

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.gmail.ngampiosauvet.task.databinding.FragmentTaskBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class TaskFragment :  Fragment() {


    private var _binding: FragmentTaskBinding? =null
    private val binding get() = _binding!!



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

    }

}