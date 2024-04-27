package com.fenil.bookshelfapp.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BookmarkEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: String, // Foreign key to UserEntity
    val bookId: String, // Foreign key to Book
)
