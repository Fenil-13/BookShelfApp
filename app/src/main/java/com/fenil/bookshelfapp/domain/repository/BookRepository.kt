package com.fenil.bookshelfapp.domain.repository

import com.fenil.bookshelfapp.DelayAwareClickListener.Resource
import com.fenil.bookshelfapp.data.local.model.AnnotationEntity
import com.fenil.bookshelfapp.data.remote.data.Book

interface BookRepository {
    suspend fun getBooks(): Resource<List<Book>?>
    suspend fun getAnnotationByBookId(bookId: String, userId: String): List<AnnotationEntity>
    suspend fun insertAnnotation(annotationEntity: AnnotationEntity)
}