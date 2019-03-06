package com.byteslabs.bookshop.ui.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.byteslabs.bookshop.db.AppDatabase
import com.byteslabs.bookshop.db.dao.AuthorDao
import com.byteslabs.bookshop.db.model.AuthorEntity
import com.byteslabs.bookshop.db.repository.AuthorRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthorViewModel(application: Application) : BaseViewModel(application) {
    private val repository : AuthorRepository
    val allAuthor : LiveData<List<AuthorEntity>>

    init {
        val authorDao : AuthorDao = AppDatabase.getDatabase(application).authorDao()
        repository = AuthorRepository(authorDao)
        allAuthor = repository.getAllAuthor()
    }

    fun insert(author : AuthorEntity) = scope.launch(Dispatchers.IO) {
        repository.insertAuthor(author)
    }

    fun delete(author : AuthorEntity) = scope.launch(Dispatchers.IO) {
        repository.deleteAuthor(author)
    }

    fun update(author : AuthorEntity) = scope.launch(Dispatchers.IO) {
        repository.updateAuthor(author)
    }

    fun deleteAllAuthor() = scope.launch(Dispatchers.IO) {
        repository.deleteAllAuthor()
    }

     class Factory(private val application: Application) : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return AuthorViewModel(application) as T
        }

    }
}