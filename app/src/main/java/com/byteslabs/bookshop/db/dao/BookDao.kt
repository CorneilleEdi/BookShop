package com.byteslabs.bookshop.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.byteslabs.bookshop.db.model.BookEntity


@Dao
interface BookDao {


    @Query("SELECT * FROM BOOK_TABLE WHERE book_author_id=:book_author_id")
    fun getAllBook(book_author_id: Long) : LiveData<List<BookEntity>>


    @Insert
    fun insertBook(book : BookEntity)

}