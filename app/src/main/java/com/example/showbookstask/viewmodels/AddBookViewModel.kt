package com.example.showbookstask.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.showbookstask.model.Book
import com.example.showbookstask.repository.BookRepository
import kotlinx.coroutines.launch

class AddBookViewModel(private val repository: BookRepository): ViewModel() {

    fun insertBook(book: Book) {
        viewModelScope.launch {
            repository.insertBook(book)
        }
    }

}