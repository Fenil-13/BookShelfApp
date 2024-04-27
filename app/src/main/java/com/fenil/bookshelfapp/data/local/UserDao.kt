package com.fenil.bookshelfapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.fenil.bookshelfapp.data.local.model.AnnotationEntity
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

    @Query("UPDATE userentity SET isLoggedIn = true WHERE email = :email")
    suspend fun loginUser(email: String)

    @Insert
    suspend fun insertAnnotation(annotationEntity: AnnotationEntity)

    @Query("SELECT * FROM AnnotationEntity WHERE userId = :userEmail AND bookId = :bookId")
    suspend fun getAnnotationsForUserAndBook(userEmail: String, bookId: String): List<AnnotationEntity>
}
