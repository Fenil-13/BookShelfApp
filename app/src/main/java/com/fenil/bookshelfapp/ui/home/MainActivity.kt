package com.fenil.bookshelfapp.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.fenil.bookshelfapp.DelayAwareClickListener.Status
import com.fenil.bookshelfapp.R
import com.fenil.bookshelfapp.data.remote.data.Book
import com.fenil.bookshelfapp.databinding.ActivityMainBinding
import com.fenil.bookshelfapp.ui.auth.AuthActivity
import com.fenil.bookshelfapp.ui.auth.AuthViewModel
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

    private val bookAdapter = BookAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observeViewModels()
        setupUi()
    }

    private fun setupUi() {
//        binding.btnLogOut.setOnClickListener(DelayAwareClickListener{
//            authViewModel.loggedInUserResponse.observe(this@MainActivity){
//                authViewModel.logout(it?.email.orEmpty())
//            }
//            startActivity(Intent(this, AuthActivity::class.java))
//        })

        binding.rvBooks.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            while(itemDecorationCount > 0){
                removeItemDecorationAt(0)
            }
            addItemDecoration(VerticalHorizontalItemDecorator(this@MainActivity, R.dimen.dp_10,R.dimen.dp_0))
            adapter = bookAdapter
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
        bookAdapter.submitList(bookListAtYear)
    }

    private fun observeViewModels() {
        authViewModel.loggedInUserResponse.observe(this) {
            if (it == null) {
                startActivity(Intent(this, AuthActivity::class.java))
            }else{
                bookViewModel.getBookList()
            }
        }
        bookViewModel.bookResponse.observe(this){
            it.data?.let { data ->
                binding.progressBar.isVisible = false
                binding.rvBooks.isVisible = true
                handleData(data)
            } ?: kotlin.run {
                when(it.status){
                    Status.LOADING -> {
                        binding.rvBooks.isVisible = false
                        binding.progressBar.isVisible = true
                    }
                    Status.SUCCESS -> {}
                    Status.ERROR,
                    Status.FAILURE,
                    Status.NO_INTERNET -> {

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
            bookAdapter.submitList(books)
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
        when (requestCode) {
            RESULT_OK -> {
                bookViewModel.getBookList()
            }
        }
    }
}