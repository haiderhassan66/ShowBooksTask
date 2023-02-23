package com.example.showbookstask.model

data class VolumeInfo(
    val authors: List<String>,
    val description: String,
    val imageLinks: ImageLinks,
    val title: String
)