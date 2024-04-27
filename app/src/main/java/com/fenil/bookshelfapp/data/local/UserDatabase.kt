package com.fenil.bookshelfapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fenil.bookshelfapp.data.local.model.AnnotationEntity
import com.fenil.bookshelfapp.data.local.model.UserEntity

const val CURRENT_DATABASE_VERSION = 1
const val DATABASE_NAME = "user_database"

@Database(entities = [UserEntity::class, AnnotationEntity::class], version = CURRENT_DATABASE_VERSION)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
