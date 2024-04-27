package com.fenil.bookshelfapp.domain.repository

import com.fenil.bookshelfapp.DelayAwareClickListener.Resource
import com.fenil.bookshelfapp.data.local.model.AnnotationEntity
import com.fenil.bookshelfapp.data.local.model.BookmarkEntity
import com.fenil.bookshelfapp.data.remote.data.Book

interface BookRepository {
    suspend fun getBooks(): Resource<List<Book>?>
    suspend fun getAnnotationByBookId(userEmail: String, bookId: String): List<AnnotationEntity>
    suspend fun insertAnnotation(annotationEntity: AnnotationEntity)
    suspend fun getBookmarkBooks(userEmail: String,bookId: String): List<BookmarkEntity>
    suspend fun insertBookmarkBooks(bookmarkEntity: BookmarkEntity)
    suspend fun deleteBookmarkBook(userEmail: String, bookId: String)
}