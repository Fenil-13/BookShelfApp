package com.fenil.bookshelfapp.ui.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.fenil.bookshelfapp.DelayAwareClickListener.DelayAwareClickListener
import com.fenil.bookshelfapp.DelayAwareClickListener.Status
import com.fenil.bookshelfapp.R
import com.fenil.bookshelfapp.data.remote.data.Book
import com.fenil.bookshelfapp.databinding.ActivityMainBinding
import com.fenil.bookshelfapp.ui.auth.AuthActivity
import com.fenil.bookshelfapp.ui.auth.AuthViewModel
import com.fenil.bookshelfapp.ui.detail.BookDetailsActivity
import com.fenil.bookshelfapp.ui.detail.BookDetailsActivity.Companion.BOOK
import com.fenil.bookshelfapp.ui.detail.BookDetailsActivity.Companion.USER
import com.fenil.bookshelfapp.ui.detail.adapter.BookAnnotationAdapter
import com.fenil.bookshelfapp.ui.home.adapter.BookAdapter
import com.fenil.bookshelfapp.ui.utils.VerticalHorizontalItemDecorator
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import java.util.SortedMap

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val authViewModel: AuthViewModel by viewModels<AuthViewModel>()
    private val bookViewModel: BookViewModel by viewModels<BookViewModel>()

    private val REQUEST_FOR_AUTH = 1001

    private val bookAnnotationAdapter = BookAdapter{
        val bookIntent = Intent(this, BookDetailsActivity::class.java)
        bookIntent.putExtra(BOOK, it)
        bookIntent.putExtra(USER, authViewModel.loggedInUserResponse.value)
        startActivity(bookIntent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observeViewModels()
        setupUi()
        authViewModel.getLoggedInUser()
    }

    private fun setupUi() {
        binding.btnLogOut.setOnClickListener(DelayAwareClickListener{
            authViewModel.loggedInUserResponse.value.let{
                authViewModel.logout(it?.email.orEmpty())
            }
            finish()
            val authIntent = Intent(this, AuthActivity::class.java)
            authIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(authIntent)
        })

        binding.rvBooks.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            while(itemDecorationCount > 0){
                removeItemDecorationAt(0)
            }
            addItemDecoration(VerticalHorizontalItemDecorator(this@MainActivity, R.dimen.dp_10,R.dimen.dp_0))
            adapter = bookAnnotationAdapter
        }

        binding.bookYearsTab.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tabLayout: TabLayout.Tab?) {
                tabLayout?.position?.let {
                    updateDataBasedOnPosition(it)
                }
            }

            override fun onTabUnselected(tabLayout: TabLayout.Tab?) {
                tabLayout?.position?.let {

                }
            }

            override fun onTabReselected(tabLayout: TabLayout.Tab?) {
                tabLayout?.position?.let {

                }
            }
        })
    }

    private fun updateDataBasedOnPosition(position: Int) {
        val year = binding.bookYearsTab.getTabAt(position)?.text
        val bookListAtYear = bookViewModel.getBookListByYear(year.toString().toInt())
        bookAnnotationAdapter.submitList(bookListAtYear)
    }

    private fun observeViewModels() {
        authViewModel.loggedInUserResponse.observe(this) {
            if (it == null) {
                startActivityForResult(Intent(this, AuthActivity::class.java), REQUEST_FOR_AUTH)
            }else{
                bookViewModel.getBookList()
            }
        }

        bookViewModel.bookResponse.observe(this){
            it.data?.let { data ->
                binding.progressBar.isVisible = false
                binding.tvError.isVisible = false
                binding.content.isVisible = true
                handleData(data)
            } ?: kotlin.run {
                when(it.status){
                    Status.LOADING -> {
                        binding.content.isVisible = false
                        binding.tvError.isVisible = false
                        binding.progressBar.isVisible = true
                    }
                    Status.SUCCESS -> {}
                    Status.ERROR,
                    Status.FAILURE,
                    Status.NO_INTERNET -> {
                        binding.tvError.isVisible = true
                        binding.progressBar.isVisible = false
                        binding.content.isVisible = false
                        binding.tvError.text = it.message
                    }
                }
            }

        }
    }

    private fun handleData(data: SortedMap<Int, List<Book>>) {
        if (data.isNotEmpty()){
            populateDataOnTabs(data)
            populateDataOnList(data[0])
        }
    }

    private fun populateDataOnList(books: List<Book>?) {
        books?.let {
            bookAnnotationAdapter.submitList(books)
        }
    }

    private fun populateDataOnTabs(yearWiseData: Map<Int, List<Book>>) {
        binding.bookYearsTab.apply {
            yearWiseData.keys.forEach {
                addTab(newTab().setText("$it"))
            }
            tabGravity = TabLayout.GRAVITY_CENTER
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_FOR_AUTH && resultCode == Activity.RESULT_OK){
            authViewModel.getLoggedInUser()
        }
    }
}