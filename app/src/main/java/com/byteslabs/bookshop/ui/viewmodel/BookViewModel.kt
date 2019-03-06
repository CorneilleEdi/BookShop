package com.byteslabs.bookshop.ui.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.byteslabs.bookshop.db.AppDatabase
import com.byteslabs.bookshop.db.dao.BookDao
import com.byteslabs.bookshop.db.model.BookEntity
import com.byteslabs.bookshop.db.repository.BookRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookViewModel(application: Application) : BaseViewModel(application) {
    private val repository : BookRepository

    init {
        val bookDao : BookDao = AppDatabase.getDatabase(application).bookDao()
        repository = BookRepository(bookDao)

    }


    fun getAllBookFromAuthor(authorId : Long) : LiveData<List<BookEntity>>
            = repository.getAllBook(authorId)


    fun insert(bookEntity: BookEntity) = scope.launch(Dispatchers.IO) {
        repository.insertBook(bookEntity)
    }

    class Factory(private val application: Application) : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return BookViewModel(application) as T
        }

    }
}