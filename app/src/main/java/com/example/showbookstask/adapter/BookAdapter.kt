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

class BookAdapter(private val listener: OnItemClickListener): ListAdapter<Book, BookAdapter.BookViewHolder>(DiffUtil()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.book_item, parent, false)
        return BookViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val item = getItem(position)
//        holder.bind(item)
//        holder.bookTitle.text
        holder.bookTitle.text = item.title
        holder.bookAuthor.text = item.authorName
        holder.bookDesc.text = item.disc
        Glide.with(holder.bookImage.context).load(item.image).placeholder(R.drawable.placeholder).into(holder.bookImage)

        holder.delete.setOnClickListener{
            listener.onItemClickListener(holder.delete, item)
        }

        holder.edit.setOnClickListener{
            listener.onItemClickListener(holder.edit, item)
        }

        holder.itemView.setOnClickListener {
            listener.onViewClickListener(holder.bookDesc)
        }
    }

    class BookViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        val bookImage: ImageView = itemView.findViewById(R.id.bookImage)
        val bookTitle: TextView = itemView.findViewById(R.id.bookName)
        val bookAuthor: TextView = itemView.findViewById(R.id.author)
        val bookDesc : TextView = itemView.findViewById(R.id.desc)
        val delete: ImageView = itemView.findViewById(R.id.delete_img)
        val edit: ImageView = itemView.findViewById(R.id.edit_img)


//        fun bind(book: Book) {
//            bookTitle.text = book.title
//            bookAuthor.text = book.authorName
//            bookDesc.text = book.disc
//            Glide.with(bookImage.context).load(book.image).placeholder(R.drawable.placeholder).into(bookImage)
//        }

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