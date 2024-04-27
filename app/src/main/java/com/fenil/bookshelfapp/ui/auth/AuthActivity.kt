package com.fenil.bookshelfapp.ui.auth

import android.app.Activity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.fenil.bookshelfapp.DelayAwareClickListener.showToast
import com.fenil.bookshelfapp.databinding.ActivityAuthBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    private var _binding: ActivityAuthBinding? = null
    private val binding get() = _binding!!

    private val authViewModel: AuthViewModel by viewModels<AuthViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observeViewModels()
    }

    private fun observeViewModels() {
        authViewModel.signUpResponse.observe(this) {
            if (it == true){
                showToast("Sign up successfully")
                setResult(Activity.RESULT_OK)
                finish()
            }else{
                showToast("Sign up failed. Please try again")
            }
        }

        authViewModel.loginResponse.observe(this) {
            if (it == null){
                showToast("email and password does not match. Login Failed")
            }else{
                showToast("Login Successfully")
                setResult(Activity.RESULT_OK)
                finish()
            }
        }
    }
}