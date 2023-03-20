package com.example.showbookstask.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.showbookstask.api.BookService
import com.example.showbookstask.model.Book
import com.example.showbookstask.database.BookDatabase
import com.example.showbookstask.utils.NetworkUtil
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class BookRepository(private val bookDatabase: BookDatabase, private val bookService: BookService,private val context: Context) {

    private val bookLiveData = MutableLiveData<List<Book>>()

    val books: LiveData<List<Book>>
        get() = bookLiveData

    fun getBooksDemo(): LiveData<List<Book>> {
        return bookDatabase.bookDAO().getBooksDemo()
    }

    suspend fun bookExist(name: String) : Boolean{
        return bookDatabase.bookDAO().bookExist(name)
    }


    suspend fun deleteBook(book: Book) {
        return bookDatabase.bookDAO().deleteBook(book)
    }


    suspend fun getSearchBooks(id: String?) : List<Book>{
        return bookDatabase.bookDAO().getSearchBooks(id)
    }

    suspend fun insertBook(book: Book){
        bookDatabase.bookDAO().insertBook(book)
    }

    suspend fun updateBook(book: Book) {
        bookDatabase.bookDAO().updateBook(book)
    }

    suspend fun getBooksInfo(query: String) {

        if(NetworkUtil.isNetworkAvailable(context) && bookDatabase.bookDAO().getBooks().isEmpty()) {
            val list: MutableList<Book> = mutableListOf()
            val result = bookService.getBooksInfo(query)
            val mainBody = result.body()
            if (mainBody != null) {
                for (i in 0 until mainBody.items.count()) {
                    val title = mainBody.items[i].volumeInfo.title
                    val authorName = mainBody.items[i].volumeInfo.authors[0]
                    val description = mainBody.items[i].volumeInfo.description
                    val image = mainBody.items[i].volumeInfo.imageLinks.thumbnail

                    val book = Book(title,authorName,description,image)

                    bookDatabase.bookDAO().insertBook(book)
                    list.add(book)
                }
                bookLiveData.postValue(list)
            }
        } else {
            val allBooks = bookDatabase.bookDAO().getBooks()
            bookLiveData.postValue(allBooks)
        }
    }

}