package com.byteslabs.bookshop.db.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.byteslabs.bookshop.db.dao.AuthorDao
import com.byteslabs.bookshop.db.model.AuthorEntity

class AuthorRepository(private val authorDao: AuthorDao) {

    fun getAllAuthor() : LiveData<List<AuthorEntity>> {
        return authorDao.getAllAuthor()
    }


    @WorkerThread
    suspend fun insertAuthor(author : AuthorEntity){
        authorDao.insertAuthor(author)
    }

    @WorkerThread
    suspend fun updateAuthor(author: AuthorEntity){
        authorDao.update(author)
    }

    @WorkerThread
    suspend fun deleteAuthor(author: AuthorEntity){
        authorDao.delete(author)
    }

    @WorkerThread
    suspend fun deleteAllAuthor(){
        authorDao.deleteAll()
    }
}