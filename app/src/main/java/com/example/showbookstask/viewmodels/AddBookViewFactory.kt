package com.example.showbookstask.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.showbookstask.repository.BookRepository

class AddBookViewFactory(private val repository: BookRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AddBookViewModel(repository) as T
    }
}