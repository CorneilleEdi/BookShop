package com.byteslabs.bookshop.db.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.byteslabs.bookshop.db.dao.BookDao
import com.byteslabs.bookshop.db.model.BookEntity

class BookRepository(private val bookDao: BookDao) {

     fun getAllBook(book_author_id : Long) : LiveData<List<BookEntity>>{
        return bookDao.getAllBook(book_author_id)
    }


    @WorkerThread
    suspend fun insertBook(book : BookEntity){
        bookDao.insertBook(book)
    }
}