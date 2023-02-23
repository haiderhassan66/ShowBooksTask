package com.example.showbookstask.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.showbookstask.R
import com.example.showbookstask.adapter.BookAdapter
import com.example.showbookstask.api.BookService
import com.example.showbookstask.api.RetrofitHelper
import com.example.showbookstask.repository.BookRepository
import com.example.showbookstask.viewmodels.MainViewModel
import com.example.showbookstask.viewmodels.MainViewModelFactory
import com.example.showbookstask.database.BookDatabase
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener,
    androidx.appcompat.widget.SearchView.OnQueryTextListener {
    lateinit var recyclerView: RecyclerView
    lateinit var adapter:BookAdapter

    lateinit var mainViewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.book_list)
        adapter = BookAdapter()

        recyclerView.hasFixedSize()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter


        val database = BookDatabase.getDatabase(applicationContext)
        val bookService = RetrofitHelper.getInstance().create(BookService::class.java)
        val repository = BookRepository(database, bookService, applicationContext)

        mainViewModel =
            ViewModelProvider(this, MainViewModelFactory(repository))[MainViewModel::class.java]

        mainViewModel.getBooksInfo("harry potter")


        mainViewModel.books.observe(this) {
            adapter.submitList(it)
        }

        mainViewModel.getBooksDemo().observe(this) {
            adapter.submitList(it)
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
            adapter.submitList(mainViewModel.getSearchBooks(searchQuery))
        }
    }
}