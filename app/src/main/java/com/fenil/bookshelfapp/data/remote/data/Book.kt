package com.fenil.bookshelfapp.data.remote.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
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
): Parcelable