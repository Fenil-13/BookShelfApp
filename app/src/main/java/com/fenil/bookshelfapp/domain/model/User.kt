package com.fenil.bookshelfapp.domain.model

import com.fenil.bookshelfapp.data.local.model.UserEntity

data class User(
    val email: String,
    val name: String,
    val password: String,
    val country: String
)

fun User.toUserEntity(isLoggedUser:Boolean) : UserEntity {
    return UserEntity(this.email, this.name, this.password, this.country,isLoggedUser)
}