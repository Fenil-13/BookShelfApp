package com.fenil.bookshelfapp.data.remote.implementation

import com.fenil.bookshelfapp.DelayAwareClickListener.Resource
import com.fenil.bookshelfapp.DelayAwareClickListener.asResource
import com.fenil.bookshelfapp.data.local.UserDao
import com.fenil.bookshelfapp.data.local.model.AnnotationEntity
import com.fenil.bookshelfapp.data.remote.interfaces.BookService
import com.fenil.bookshelfapp.data.remote.data.Book
import com.fenil.bookshelfapp.domain.repository.BookRepository

class BookImpl(
    private val bookService: BookService,
    private val userDao: UserDao
) : BookRepository {

    override suspend fun getBooks(): Resource<List<Book>?> {
        return bookService.getBooks().asResource()
    }

    override suspend fun getAnnotationByBookId(
        userEmail: String,
        bookId: String
    ): List<AnnotationEntity> {
        return userDao.getAnnotationsForUserAndBook(userEmail, bookId)
    }

    override suspend fun insertAnnotation(annotationEntity: AnnotationEntity) {
        userDao.insertAnnotation(annotationEntity)
    }

}
