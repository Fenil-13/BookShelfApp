package com.fenil.bookshelfapp.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fenil.bookshelfapp.R
import com.fenil.bookshelfapp.data.remote.data.Book
import com.fenil.bookshelfapp.databinding.ItemBookBinding
import java.text.SimpleDateFormat
import java.util.Locale

class BookAdapter() : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    private val differ = AsyncListDiffer(this, BookDiffCallback())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = ItemBookBinding.inflate(
           LayoutInflater.from(parent.context), parent, false
        )
        return BookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = differ.currentList.size

    fun submitList(list: List<Book>) {
        differ.submitList(list)
    }

    class BookViewHolder(private val binding: ItemBookBinding) : RecyclerView.ViewHolder(binding.root) {
        private val simpleDateFormat = SimpleDateFormat("dd MMMM yyyy, HH:mm:ss", Locale.ENGLISH)
        fun bind(book: Book?) {
            book?.let {
                binding.apply {
                    Glide.with(binding.root.context)
                        .load(book.image.orEmpty())
                        .placeholder(R.drawable.ic_book_default)
                        .into(binding.ivBook)
                    tvBookTitle.text = book.title.orEmpty()
                    tvBookScore.text = book.score.toString()

                    tvBookPublishDate.text = book.publishedChapterDate?.let { timeStamp ->
                        getDateString(timeStamp)
                    }
                }
            }
        }
        private fun getDateString(time: Long) : String = simpleDateFormat.format(time * 1000L)

    }
    class BookDiffCallback : DiffUtil.ItemCallback<Book>() {
        override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem == newItem
        }
    }
}


