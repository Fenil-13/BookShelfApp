package com.fenil.bookshelfapp.domain.repository

import com.fenil.bookshelfapp.domain.model.User

interface UserRepository {
    suspend fun signUp(user: User): Boolean
    suspend fun login(email: String, password: String): User?
    suspend fun getLoggedInUser(): User?
    suspend fun logout(email: String)
}