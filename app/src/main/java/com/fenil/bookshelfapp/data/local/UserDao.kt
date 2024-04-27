package com.fenil.bookshelfapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.fenil.bookshelfapp.data.local.model.AnnotationEntity
import com.fenil.bookshelfapp.data.local.model.BookmarkEntity
import com.fenil.bookshelfapp.data.local.model.UserEntity

@Dao
interface UserDao {
    @Insert
    suspend fun insert(user: UserEntity)

    @Query("SELECT * FROM userentity WHERE email = :email AND password = :password")
    suspend fun getUserByEmail(email: String, password: String): UserEntity?

    @Query("SELECT * FROM userentity WHERE isLoggedIn = :isLoggedIn")
    suspend fun getLoggedInUser(isLoggedIn :String): List<UserEntity>?

    @Query("UPDATE userentity SET isLoggedIn = :isLoggedIn WHERE email = :email")
    suspend fun logoutUser(isLoggedIn :String, email: String)

    @Query("UPDATE userentity SET isLoggedIn = :isLoggedIn WHERE email = :email")
    suspend fun loginUser(isLoggedIn :String, email: String)

    @Insert
    suspend fun insertAnnotation(annotationEntity: AnnotationEntity)

    @Query("SELECT * FROM AnnotationEntity WHERE userId = :userEmail AND bookId = :bookId")
    suspend fun getAnnotationsForUserAndBook(userEmail: String, bookId: String): List<AnnotationEntity>

    @Insert
    suspend fun insertBookmarkBooks(bookmarkEntity: BookmarkEntity)

    @Query("SELECT * FROM BookmarkEntity WHERE userId = :userEmail AND bookId = :bookId")
    suspend fun getBookmarkedBooks(userEmail: String, bookId: String): List<BookmarkEntity>

    @Query("DELETE FROM BookmarkEntity WHERE userId = :userEmail AND bookId = :bookId")
    suspend fun deleteBookmarkBook(userEmail: String, bookId: String)

}
