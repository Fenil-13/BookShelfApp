package com.fenil.bookshelfapp.ui.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.fenil.bookshelfapp.DelayAwareClickListener.DelayAwareClickListener
import com.fenil.bookshelfapp.data.local.model.AnnotationEntity
import com.fenil.bookshelfapp.databinding.ItemBookAnnotationBinding

class BookAnnotationAdapter(private val onBookClick: (annotationEntity: AnnotationEntity) -> Unit) : RecyclerView.Adapter<BookAnnotationAdapter.BookAnnotationViewHolder>() {

    private val differ = AsyncListDiffer(this, BookAnnotationDiffCallback())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookAnnotationViewHolder {
        val binding = ItemBookAnnotationBinding.inflate(
           LayoutInflater.from(parent.context), parent, false
        )
        return BookAnnotationViewHolder(onBookClick, binding)
    }

    override fun onBindViewHolder(holder: BookAnnotationViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = differ.currentList.size

    fun submitList(list: List<AnnotationEntity>) {
        differ.submitList(list)
    }

    class BookAnnotationViewHolder(val onBookAnnotationClick: (annotationEntity: AnnotationEntity) -> Unit, private val binding: ItemBookAnnotationBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(annotationEntity: AnnotationEntity?) {
            annotationEntity?.let {
                binding.apply {
                    tvBookTitle.text = it.text
                }
            }

            binding.root.setOnClickListener(DelayAwareClickListener{
                annotationEntity?.let(onBookAnnotationClick)
            })
        }

    }
    class BookAnnotationDiffCallback : DiffUtil.ItemCallback<AnnotationEntity>() {
        override fun areItemsTheSame(oldItem: AnnotationEntity, newItem: AnnotationEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AnnotationEntity, newItem: AnnotationEntity): Boolean {
            return oldItem == newItem
        }
    }
}


