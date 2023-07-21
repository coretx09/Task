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
import com.gmail.ngampiosauvet.task.R
import com.gmail.ngampiosauvet.task.databinding.FragmentLoginBinding
import com.gmail.ngampiosauvet.task.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

private const val TAG = "TaskFragment"

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AccountViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loginUiState.collect() {
                    when(it) {
                         Resource.Loading -> {

                             Log.d(TAG, "ConnectUserWithEmail:loading")
                            Toast.makeText(context, " ACCOUNT", Toast.LENGTH_SHORT).show()
                        }

                        is Resource.Success -> {
                            Log.d(TAG, "ConnectUserWithEmail:success")
                            Toast.makeText(context, "WELCOME", Toast.LENGTH_SHORT).show()
                            findNavController().navigateUp()
                        }
                        is Resource.Failure
                        -> {
                            Log.w(TAG, "connectUserWithEmail:failure", )

                            Toast.makeText(context, " YOU DON'T HAVE A ACCOUNT", Toast.LENGTH_SHORT).show()
                        }

                        else -> {}
                    }

                }
            }
        }

        binding.signInButton.setOnClickListener {
            Log.d(TAG, "click", )
            viewModel.login(
                binding.editEmail.text.toString(),
                binding.editPassword.text.toString()
            )



        }
    }


}