package com.example.showbookstask.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.showbookstask.R
import com.example.showbookstask.api.BookService
import com.example.showbookstask.api.RetrofitHelper
import com.example.showbookstask.model.Book
import com.example.showbookstask.database.BookDatabase
import com.example.showbookstask.repository.BookRepository
import com.example.showbookstask.viewmodels.AddBookViewFactory
import com.example.showbookstask.viewmodels.AddBookViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddBookActivity : AppCompatActivity() {

    lateinit var title: EditText
    lateinit var authorName: EditText
    lateinit var desc: EditText
    lateinit var btn: Button

    lateinit var addBookViewModel: AddBookViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_book)

        supportActionBar!!.title = "New Book"

        val database = BookDatabase.getDatabase(applicationContext)
        val bookService = RetrofitHelper.getInstance().create(BookService::class.java)
        val repository = BookRepository(database, bookService, applicationContext)

        addBookViewModel = ViewModelProvider(this, AddBookViewFactory(repository))[AddBookViewModel::class.java]

        title = findViewById(R.id.bname)
        authorName = findViewById(R.id.aname)
        desc = findViewById(R.id.des)
        btn = findViewById(R.id.aBtn)

        btn.setOnClickListener {
            if(title.text.isNotEmpty()){
                val isExist: Boolean = addBookViewModel.bookExist(title.text.toString())

                if(isExist){
                    Toast.makeText(this, "Change book name, it already exists", Toast.LENGTH_SHORT).show()
                } else {
                    val book = Book(title.text.toString(),authorName.text.toString(),desc.text.toString(),"")
                    addBookViewModel.insertBook(book)
                    Toast.makeText(this, "Book added", Toast.LENGTH_SHORT).show()
                }

            } else {
                Toast.makeText(this, "Title can't be empty", Toast.LENGTH_SHORT).show()
            }

        }

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}