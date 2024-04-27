package com.fenil.bookshelfapp.ui.detail

import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.widget.EditText
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.fenil.bookshelfapp.DelayAwareClickListener.DelayAwareClickListener
import com.fenil.bookshelfapp.R
import com.fenil.bookshelfapp.data.local.model.AnnotationEntity
import com.fenil.bookshelfapp.data.remote.data.Book
import com.fenil.bookshelfapp.databinding.ActivityBookDetailsBinding
import com.fenil.bookshelfapp.domain.model.User
import com.fenil.bookshelfapp.ui.detail.adapter.BookAnnotationAdapter
import com.fenil.bookshelfapp.ui.detail.dialogs.BookAnnotationDialog
import com.fenil.bookshelfapp.ui.home.BookViewModel
import com.fenil.bookshelfapp.ui.utils.VerticalHorizontalItemDecorator
import com.fenil.bookshelfapp.ui.utils.getDateString
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class BookDetailsActivity : AppCompatActivity() {

    companion object{
        const val BOOK = "book"
        const val USER = "user"
    }

    private var _binding: ActivityBookDetailsBinding? = null
    private val binding get() = _binding!!

    private val bookViewModel: BookViewModel by viewModels<BookViewModel>()

    private val bookAnnotationAdapter = BookAnnotationAdapter{ }

    private var user:User? = null
    private var book:Book? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityBookDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observeViewModels()
        setupUi()
    }

    private fun setupUi() {

        book = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(BOOK, Book::class.java)
        } else {
            intent.getParcelableExtra(BOOK)
        }

        user = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(USER, User::class.java)
        } else {
            intent.getParcelableExtra(USER)
        }

        book?.let {
            binding.apply {
                Glide.with(binding.root.context)
                    .load(book?.image.orEmpty())
                    .placeholder(R.drawable.ic_book_default)
                    .into(binding.ivBook)
                tvBookTitle.text = book?.title.orEmpty()
                tvBookTitleSmall.text = book?.title.orEmpty()
                tvBookScore.text = book?.score.toString()

                tvBookPublishDate.text = book?.publishedChapterDate?.let { timeStamp ->
                    getDateString(timeStamp)
                }
            }
        }

        bookViewModel.getBookAnnotationByUser(user?.email.orEmpty(), book?.id.orEmpty())

        binding.rvAnnotation.apply {
            layoutManager = LinearLayoutManager(this@BookDetailsActivity)
            while (itemDecorationCount > 0){
                removeItemDecorationAt(0)
            }
            addItemDecoration(VerticalHorizontalItemDecorator(this@BookDetailsActivity, R.dimen.dp_10,R.dimen.dp_0))
            adapter = bookAnnotationAdapter
        }

        binding.btnAddAnnotation.setOnClickListener(DelayAwareClickListener{
            openAnnotationDialog()
        })
    }

    private fun openAnnotationDialog() {
        val input = EditText(this)
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this)
        alertDialog.setTitle("Add Annotation");
        alertDialog.setMessage("annotation for book you are reading");

        val lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        input.setLayoutParams(lp);
        alertDialog.setView(input);

        alertDialog.setPositiveButton("Yes Add"
        ) { dialog, whichButton -> //What ever you want to do with the value
            if (input.text.toString().isNotEmpty()) {
                AnnotationEntity(
                    userId = user?.email.orEmpty(),
                    bookId = book?.id.orEmpty(),
                    text = input.text.toString()
                ).let {
                    bookViewModel.insertAnnotation(it)
                }
            }

        }

        alertDialog.setNegativeButton("Don't Add"
        ) { dialog, whichButton ->
            // what ever you want to do with No option.
            dialog.dismiss()
        }

        alertDialog.show()
    }


    private fun observeViewModels() {
        bookViewModel.annotationListResponse.observe(this){
            it?.let { data ->
                handleData(data)
            }
        }
    }

    private fun handleData(data: List<AnnotationEntity>) {
        if (data.isNotEmpty()){
            bookAnnotationAdapter.submitList(data)
        }
    }

}