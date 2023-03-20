package com.example.showbookstask.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.showbookstask.model.Book

@Dao
interface BookDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: Book)

    @Update
    suspend fun updateBook(book: Book)

    @Delete
    suspend fun deleteBook(book: Book)

    @Query("SELECT * FROM books")
    suspend fun getBooks() : List<Book>

    @Query("SELECT * FROM books")
    fun getBooksDemo() : LiveData<List<Book>>

    @Query("SELECT * FROM books where title LIKE :id")
    suspend fun getSearchBooks(id: String?) : List<Book>

    @Query("SELECT EXISTS(SELECT * FROM books where title = :name)")
    suspend fun bookExist(name: String?) : Boolean
}