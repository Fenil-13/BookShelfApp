package com.fenil.bookshelfapp.data.remote.implementation

import com.fenil.bookshelfapp.DelayAwareClickListener.Resource
import com.fenil.bookshelfapp.DelayAwareClickListener.asResource
import com.fenil.bookshelfapp.data.local.UserDao
import com.fenil.bookshelfapp.data.local.model.AnnotationEntity
import com.fenil.bookshelfapp.data.local.model.BookmarkEntity
import com.fenil.bookshelfapp.data.remote.interfaces.BookService
import com.fenil.bookshelfapp.data.remote.data.Book
import com.fenil.bookshelfapp.di.NetworkHelper
import com.fenil.bookshelfapp.domain.repository.BookRepository
import javax.inject.Inject

class BookImpl @Inject constructor(
    private val bookService: BookService,
    private val userDao: UserDao,
    private val networkHelper: NetworkHelper,
) : BookRepository {

    override suspend fun getBooks(): Resource<List<Book>?> {
        if (!networkHelper.isNetworkConnected()){
            return Resource.noInternet()
        }
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

    override suspend fun getBookmarkBooks(userEmail: String, bookId: String): List<BookmarkEntity> {
        return userDao.getBookmarkedBooks(userEmail, bookId)
    }

    override suspend fun insertBookmarkBooks(bookmarkEntity: BookmarkEntity) {
        userDao.insertBookmarkBooks(bookmarkEntity)
    }

    override suspend fun deleteBookmarkBook(userEmail: String, bookId: String) {
        userDao.deleteBookmarkBook(userEmail, bookId)
    }

}
