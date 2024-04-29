package com.fenil.bookshelfapp.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.fenil.bookshelfapp.DelayAwareClickListener.showToast
import com.fenil.bookshelfapp.databinding.ActivityAuthBinding
import com.fenil.bookshelfapp.ui.home.MainActivity
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
                finish()
                redirectToHomeScreen()
            }else{
                showToast("Sign up failed. Please try again")
            }
        }

        authViewModel.loginResponse.observe(this) {
            if (it == null){
                showToast("email and password does not match. Login Failed")
            }else{
                showToast("Login Successfully")
                finish()
                redirectToHomeScreen()
            }
        }
    }

    private fun redirectToHomeScreen() {
        val homeIntent = Intent(this, MainActivity::class.java)
        homeIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(homeIntent)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        setResult(RESULT_CANCELED)
    }
}