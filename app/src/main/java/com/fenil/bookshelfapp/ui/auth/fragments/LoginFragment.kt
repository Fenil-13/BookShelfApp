package com.fenil.bookshelfapp.ui.auth.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.fenil.bookshelfapp.databinding.FragmentLoginBinding
import com.fenil.bookshelfapp.DelayAwareClickListener.DelayAwareClickListener
import com.fenil.bookshelfapp.DelayAwareClickListener.isEmpty
import com.fenil.bookshelfapp.DelayAwareClickListener.passwordPattern
import com.fenil.bookshelfapp.DelayAwareClickListener.showToast
import com.fenil.bookshelfapp.domain.model.User
import com.fenil.bookshelfapp.ui.auth.AuthViewModel
import com.fenil.bookshelfapp.ui.auth.CountryViewModel

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val authViewModel: AuthViewModel by activityViewModels<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        observeViewModels()
    }

    private fun observeViewModels() {
        authViewModel.loginResponse.observe(viewLifecycleOwner) {
            if (it == null){
                context?.showToast("email and password does not match. Login Failed")
            }else{

            }
        }
    }

    private fun setupUi() {
        binding.btnSignUp.setOnClickListener(DelayAwareClickListener{
            LoginFragmentDirections.actionLoginToSignUp().let {
                findNavController().navigate(it)
            }
        })

        binding.btnLogin.setOnClickListener(DelayAwareClickListener{
          if (binding.etEmail.isEmpty()){
                context?.showToast("Email cannot be empty")
                return@DelayAwareClickListener
            }else if (binding.etPassword.isEmpty()){
                context?.showToast("Password cannot be empty")
                return@DelayAwareClickListener
            }else if (!passwordPattern.matches(binding.etPassword.text.toString())){
                context?.showToast("min 8 characters with atleast one number, special characters[!@#\$%&()],\n" +
                        "one lowercase letter, and one uppercase letter is mandatory.")
                return@DelayAwareClickListener
            }
            authViewModel.login(binding.etEmail.text.toString(), binding.etPassword.text.toString())
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
