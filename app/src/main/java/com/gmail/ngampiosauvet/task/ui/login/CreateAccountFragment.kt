package com.gmail.ngampiosauvet.task.ui.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.gmail.ngampiosauvet.task.databinding.FragmentCreateAccountBinding
import com.gmail.ngampiosauvet.task.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

private const val TAG = "TaskFragment"
@AndroidEntryPoint
class CreateAccountFragment : Fragment() {

    private var _binding: FragmentCreateAccountBinding? = null
    private val binding get() = _binding!!

    private val viewModel:AccountViewModel by viewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCreateAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.signupUiState.collect() {
                    when(it) {
                        Resource.Loading -> {

                            Log.d(TAG, "CreateUserWithEmail:loading")
                            Toast.makeText(context, " loading", Toast.LENGTH_SHORT).show()
                        }

                        is Resource.Success -> {
                            Log.d(TAG, "CreateUserWithEmail:success")
                            Toast.makeText(context, "YOU CREATED", Toast.LENGTH_SHORT).show()
                            findNavController().navigateUp()
                        }
                        is Resource.Failure
                        -> {
                            Log.w(TAG, "createUserWithEmail:failure", )

                            Toast.makeText(context, " failure ", Toast.LENGTH_SHORT).show()
                        }

                        else -> {}
                    }

                }
            }
        }

        binding.createAccountButton.setOnClickListener {
            viewModel.signup(
                binding.editEmail.text.toString(),
                binding.editPassword.text.toString()
            )


        }



    }

}