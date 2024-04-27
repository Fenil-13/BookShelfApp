package com.fenil.bookshelfapp.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fenil.bookshelfapp.DelayAwareClickListener.DelayAwareClickListener
import com.fenil.bookshelfapp.R
import com.fenil.bookshelfapp.data.remote.data.Book
import com.fenil.bookshelfapp.databinding.ItemBookBinding
import com.fenil.bookshelfapp.ui.detail.adapter.BookAnnotationAdapter
import com.fenil.bookshelfapp.ui.utils.getDateString
import java.text.SimpleDateFormat
import java.util.Locale

class BookAdapter(private val onBookClick: (book: Book) -> Unit) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    private val differ = AsyncListDiffer(this, BookDiffCallback())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = ItemBookBinding.inflate(
           LayoutInflater.from(parent.context), parent, false
        )
        return BookViewHolder(onBookClick, binding)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = differ.currentList.size

    fun submitList(list: List<Book>) {
        differ.submitList(list)
    }

    class BookViewHolder(val onBookClick: (book: Book) -> Unit, private val binding: ItemBookBinding) : RecyclerView.ViewHolder(binding.root) {
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

            binding.root.setOnClickListener(DelayAwareClickListener{
                book?.let(onBookClick)
            })
        }
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


