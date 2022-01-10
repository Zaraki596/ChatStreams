package com.example.chatstreams.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.chatstreams.R
import com.example.chatstreams.databinding.FragmentLoginBinding
import com.example.chatstreams.ui.BindingFragment
import com.example.chatstreams.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BindingFragment<FragmentLoginBinding>() {

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentLoginBinding::inflate

    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnConfirm.setOnClickListener {
            setupConnectingUiState()
            viewModel.connectUser(binding.etUsername.text.toString())
        }
        /*
        * Setting error value null here so that when user enter the name again he doesn't get the
        * same error popup
        * */
        binding.etUsername.addTextChangedListener {
            binding.etUsername.error = null
        }
        subscribeToEvents()

    }

    private fun subscribeToEvents() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.loginEvent.collect { event ->
                when (event) {
                    LoginViewModel.LogInEvent.ErrorInputTooShort -> {
                        setupIdleUiState()
                        binding.etUsername.error = getString(R.string.error_username_too_short, Constants.MIN_USERNAME_LENGTH)
                    }
                    is LoginViewModel.LogInEvent.ErrorLogin -> {
                        setupIdleUiState()
                        Toast.makeText(requireContext(), event.errorMessage, Toast.LENGTH_LONG).show()
                    }
                    LoginViewModel.LogInEvent.Success -> {
                        setupIdleUiState()
                        Toast.makeText(requireContext(), "Success Login", Toast.LENGTH_LONG).show()
                        findNavController().navigate(R.id.action_loginFragment_to_channelFragment)
                    }
                }
            }
        }
    }

    private fun setupConnectingUiState() {
        binding.progressBar.isVisible = true
        binding.btnConfirm.isEnabled = false
    }

    private fun setupIdleUiState() {
        binding.progressBar.isVisible = false
        binding.btnConfirm.isEnabled = true
    }
}