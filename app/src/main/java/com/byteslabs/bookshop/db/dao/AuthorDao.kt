package com.byteslabs.bookshop.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.byteslabs.bookshop.db.model.AuthorEntity

@Dao
interface AuthorDao {

    @Query("SELECT * FROM author_table ORDER  BY author_name ASC")
    fun getAllAuthor() : LiveData<List<AuthorEntity>>

    @Insert
    fun insertAuthor(author : AuthorEntity)

    @Update
    fun update(author : AuthorEntity)

    @Delete
    fun delete(author : AuthorEntity)

    @Query("DELETE  FROM  author_table")
    fun deleteAll()
}