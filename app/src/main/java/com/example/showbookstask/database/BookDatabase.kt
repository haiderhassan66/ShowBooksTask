package com.example.showbookstask.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.showbookstask.model.Book

@Database(entities = [Book::class], version = 3)
abstract class BookDatabase : RoomDatabase() {

    abstract fun bookDAO() : BookDAO

    companion object {

        @Volatile
        private var INSTANCE: BookDatabase? = null

        fun getDatabase(context: Context): BookDatabase {
            if(INSTANCE == null){
                synchronized(this){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                                BookDatabase::class.java, "bookDB").fallbackToDestructiveMigration().build()
                }
            }
            return INSTANCE!!
        }

    }
}