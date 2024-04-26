package com.fenil.bookshelfapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.fenil.bookshelfapp.data.local.model.UserEntity

@Dao
interface UserDao {
    @Insert
    suspend fun insert(user: UserEntity)

    @Query("SELECT * FROM userentity WHERE email = :email")
    suspend fun getUserByEmail(email: String): UserEntity?

    @Query("SELECT * FROM userentity WHERE isLoggedIn = true")
    suspend fun getLoggedInUser(): List<UserEntity>?

    @Query("UPDATE userentity SET isLoggedIn = false WHERE email = :email")
    suspend fun logoutUser(email: String)
}
