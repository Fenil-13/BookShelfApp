package com.fenil.bookshelfapp.data.local

import com.fenil.bookshelfapp.data.local.model.toUser
import com.fenil.bookshelfapp.domain.model.User
import com.fenil.bookshelfapp.domain.model.toUserEntity
import com.fenil.bookshelfapp.domain.repository.UserRepository

class UserRepositoryImpl(private val userDao: UserDao) : UserRepository {

    override suspend fun signUp(user: User): Boolean {
        return try {
            userDao.insert(user.toUserEntity(true))
            true
        }catch (e:Exception){
            false
        }
    }

    override suspend fun login(email: String, password: String): User? {
        val user = userDao.getUserByEmail(email)
        if (user!=null) userDao.loginUser(email)
        return user?.toUser()
    }

    override suspend fun getLoggedInUser(): User? {
        val user = userDao.getLoggedInUser()?.firstOrNull()
        return user?.toUser()
    }

    override suspend fun logout(email: String) {
        userDao.logoutUser(email)
    }
}
