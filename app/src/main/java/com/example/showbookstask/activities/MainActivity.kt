package com.example.showbookstask.activities

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.showbookstask.R
import com.example.showbookstask.adapter.BookAdapter
import com.example.showbookstask.adapter.OnItemClickListener
import com.example.showbookstask.api.BookService
import com.example.showbookstask.api.RetrofitHelper
import com.example.showbookstask.repository.BookRepository
import com.example.showbookstask.viewmodels.MainViewModel
import com.example.showbookstask.viewmodels.MainViewModelFactory
import com.example.showbookstask.database.BookDatabase
import com.example.showbookstask.databinding.EditBookDialogBinding
import com.example.showbookstask.model.Book
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener,
    androidx.appcompat.widget.SearchView.OnQueryTextListener, OnItemClickListener {
    lateinit var recyclerView: RecyclerView
    lateinit var bookadapter:BookAdapter

    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.book_list)
        bookadapter = BookAdapter(this)

        recyclerView.hasFixedSize()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = bookadapter


        val database = BookDatabase.getDatabase(applicationContext)
        val bookService = RetrofitHelper.getInstance().create(BookService::class.java)
        val repository = BookRepository(database, bookService, applicationContext)

        mainViewModel =
            ViewModelProvider(this, MainViewModelFactory(repository))[MainViewModel::class.java]

        mainViewModel.getBooksInfo("harry potter")

        
        mainViewModel.books.observe(this) {
            bookadapter.submitList(it)
        }

        mainViewModel.getBooksDemo().observe(this) {
            bookadapter.submitList(it)
        }


    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menus, menu)
        val search = menu?.findItem(R.id.search)
        val searchView = search?.actionView as? androidx.appcompat.widget.SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.add) {
            startActivity(Intent(this, AddBookActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if(query != null) {
            searchDatabase(query)
        }
        return true
    }

    private fun searchDatabase(query: String?) {
        val searchQuery = "%$query%"
        GlobalScope.launch {
            bookadapter.submitList(mainViewModel.getSearchBooks(searchQuery))
        }
    }

    @SuppressLint("ResourceAsColor")
    override fun onItemClickListener(view: View, book: Book) {
        when(view.id){
            R.id.delete_img -> {
                GlobalScope.launch {
                    mainViewModel.deleteBook(book)
                }
            }
            R.id.edit_img -> {
                var dialog = Dialog(this)
                dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
//                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.setContentView(R.layout.edit_book_dialog)
//                dialog.setCancelable(false)
                val title = dialog.findViewById(R.id.bn) as TextView
                val submit = dialog.findViewById<Button>(R.id.submit_btn)
                val authorName = dialog.findViewById<EditText>(R.id.author_edt)
                val description = dialog.findViewById<EditText>(R.id.desc_edt)
                title.text = book.title

                dialog.show()

                submit.setOnClickListener{
                    book.authorName = authorName.text.toString()
                    book.disc = description.text.toString()
                    GlobalScope.launch {
                        mainViewModel.updateBook(book)
                    }

                    dialog.dismiss()
                }
            }
        }
    }

    override fun onViewClickListener(view: View) {
//        Toast.makeText(this, position.toString(), Toast.LENGTH_SHORT).show()
        val txt: TextView = view as TextView
        txt.maxLines = 10
    }
}