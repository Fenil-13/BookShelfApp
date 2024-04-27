package com.fenil.bookshelfapp.data.remote.interfaces

import com.fenil.bookshelfapp.data.remote.data.Book
import retrofit2.Call
import retrofit2.http.GET

interface BookService {
    @GET("b/CNGI")
    fun getBooks(): Call<List<Book>?>
}