package com.example.showbookstask.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.showbookstask.model.Book
import com.example.showbookstask.repository.BookRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: BookRepository): ViewModel() {

    val books: LiveData<List<Book>>
    get() = repository.books

    suspend fun getSearchBooks(id: String?) : List<Book>{
        return repository.getSearchBooks(id)

    }

    suspend fun deleteBook(book: Book) {
        repository.deleteBook(book)
    }

    suspend fun updateBook(book: Book) {
        repository.updateBook(book)
    }


    fun getBooksDemo(): LiveData<List<Book>> {
        return repository.getBooksDemo()
    }


    fun getBooksInfo(query: String) {
        viewModelScope.launch {
            repository.getBooksInfo(query)
        }
    }
}