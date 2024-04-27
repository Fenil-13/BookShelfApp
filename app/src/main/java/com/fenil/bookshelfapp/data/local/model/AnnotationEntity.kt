package com.fenil.bookshelfapp.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AnnotationEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: String, // Foreign key to UserEntity
    val bookId: String, // Foreign key to Book
    val text: String
)