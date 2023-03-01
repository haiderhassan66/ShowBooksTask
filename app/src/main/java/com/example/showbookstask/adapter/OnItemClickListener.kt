package com.example.showbookstask.adapter

import android.view.View
import com.example.showbookstask.model.Book

interface OnItemClickListener {

    fun onItemClickListener(view: View, book: Book)

    fun onViewClickListener(view: View)
}