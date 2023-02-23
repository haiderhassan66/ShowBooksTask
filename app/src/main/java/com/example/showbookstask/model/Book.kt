package com.example.showbookstask.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class Book (
    @PrimaryKey var title: String,
    var authorName: String, var disc: String, var image: String)