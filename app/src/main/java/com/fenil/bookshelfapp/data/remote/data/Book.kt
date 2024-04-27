package com.fenil.bookshelfapp.data.remote.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Book(
    @PrimaryKey
    @SerializedName("id")
    val id: String?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("score")
    val score: Double?,
    @SerializedName("popularity")
    val popularity: Int?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("publishedChapterDate")
    val publishedChapterDate: Long?
)