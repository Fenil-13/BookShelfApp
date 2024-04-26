package com.fenil.bookshelfapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fenil.bookshelfapp.DelayAwareClickListener.DelayAwareClickListener
import com.fenil.bookshelfapp.databinding.ActivityAuthBinding
import com.fenil.bookshelfapp.databinding.ActivityMainBinding
import com.fenil.bookshelfapp.ui.auth.AuthActivity
import com.fenil.bookshelfapp.ui.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val authViewModel: AuthViewModel by viewModels<AuthViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observeViewModels()
        setupUi()
    }

    private fun setupUi() {
        binding.btnLogOut.setOnClickListener(DelayAwareClickListener{
            authViewModel.loggedInUserResponse.observe(this@MainActivity){
                authViewModel.logout(it?.email.orEmpty())
            }
            startActivity(Intent(this, AuthActivity::class.java))
        })
    }

    private fun observeViewModels() {
        authViewModel.loggedInUserResponse.observe(this) {
            if (it == null) {
                startActivity(Intent(this, AuthActivity::class.java))
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            RESULT_OK -> {

            }
        }
    }
}