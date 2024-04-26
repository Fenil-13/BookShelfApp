package com.fenil.bookshelfapp.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fenil.bookshelfapp.domain.model.User

@Entity
data class UserEntity(
    @PrimaryKey val email: String,
    val name: String,
    val password: String,
    val country: String,
    val isLoggedIn: Boolean = false
)

fun UserEntity.toUser(): User {
    return User(email, name, password, country)
}
