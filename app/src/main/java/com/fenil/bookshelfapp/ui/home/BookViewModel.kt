package com.fenil.bookshelfapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fenil.bookshelfapp.DelayAwareClickListener.Resource
import com.fenil.bookshelfapp.data.local.model.AnnotationEntity
import com.fenil.bookshelfapp.data.remote.data.Book
import com.fenil.bookshelfapp.domain.repository.BookRepository
import com.fenil.bookshelfapp.ui.utils.getYearFromTimestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.SortedMap
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(
    private val bookRepository: BookRepository
) : ViewModel() {

    private val _bookResponse = MutableLiveData<Resource<SortedMap<Int, List<Book>>?>>()
    val bookResponse: LiveData<Resource<SortedMap<Int, List<Book>>?>> = _bookResponse

    private val _annotationListResponse = MutableLiveData<List<AnnotationEntity>?>()
    val annotationListResponse: LiveData<List<AnnotationEntity>?> = _annotationListResponse


    fun getBookList() {
        viewModelScope.launch {
            val response = bookRepository.getBooks()
            _bookResponse.value = response.map {
                it?.groupBy(
                    keySelector = { book -> getYearFromTimestamp(book.publishedChapterDate ?: 0L) },
                    valueTransform = { it }
                )?.filter { map ->
                    map.value.isNotEmpty()
                }?.toSortedMap()
            }
        }
    }

    fun getBookListByYear(year: Int): List<Book> {
        return bookResponse.value?.data?.get(year) ?: emptyList()
    }

    fun getBookAnnotationByUser(userEmail: String, bookId: String) {
        viewModelScope.launch {
            val response = bookRepository.getAnnotationByBookId(userEmail, bookId)
            _annotationListResponse.value = response
        }
    }

    fun insertAnnotation(annotationEntity: AnnotationEntity) {
        viewModelScope.launch {
            bookRepository.insertAnnotation(annotationEntity)
            delay(500)
            getBookAnnotationByUser(annotationEntity.userId, annotationEntity.bookId)

        }
    }

}