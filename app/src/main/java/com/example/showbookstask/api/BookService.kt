package com.example.showbookstask.api

import com.example.showbookstask.model.BookDetails
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BookService {

    @GET("/books/v1/volumes")
    suspend fun getBooksInfo(@Query("q") bookName: String) :Response<BookDetails>
}