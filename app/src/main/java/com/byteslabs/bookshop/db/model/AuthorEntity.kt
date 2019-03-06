package com.byteslabs.bookshop.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "author_table")
data class AuthorEntity(
    @ColumnInfo(name = "author_id")
    @PrimaryKey(autoGenerate = true)
    var author_id: Long = 0,

    @ColumnInfo(name = "author_name")
    var author_name : String,

    @ColumnInfo(name = "author_dob")
    var author_dob : String? = null,

    @ColumnInfo(name = "author_genre")
    var author_genre : String
) {
}