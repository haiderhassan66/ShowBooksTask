package com.example.showbookstask.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.showbookstask.R
import com.example.showbookstask.model.Book

class BookAdapter(): ListAdapter<Book, BookAdapter.BookViewHolder>(DiffUtil()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.book_item, parent, false)
        return BookViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class BookViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val bookImage: ImageView = itemView.findViewById(R.id.bookImage)
        private val bookTitle: TextView = itemView.findViewById(R.id.bookName)
        private val bookAuthor: TextView = itemView.findViewById(R.id.author)
        private val bookDesc : TextView = itemView.findViewById(R.id.desc)

        fun bind(book: Book) {
            bookTitle.text = book.title
            bookAuthor.text = book.authorName
            bookDesc.text = book.disc
            Glide.with(bookImage.context).load(book.image).placeholder(R.drawable.placeholder).into(bookImage)
        }
    }

    class DiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<Book>() {
        override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem == newItem
        }

    }

}