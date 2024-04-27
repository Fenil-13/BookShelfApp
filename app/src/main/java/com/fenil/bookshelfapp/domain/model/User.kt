package com.fenil.bookshelfapp.domain.model

import android.os.Parcelable
import com.fenil.bookshelfapp.data.local.model.UserEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val email: String,
    val name: String,
    val password: String,
    val country: String
): Parcelable

fun User.toUserEntity(isLoggedUser:Boolean) : UserEntity {
    return UserEntity(this.email, this.name, this.password, this.country,isLoggedUser)
}