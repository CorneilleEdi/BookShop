package com.byteslabs.bookshop.db.model

import androidx.room.*
import androidx.room.ForeignKey.CASCADE


@Entity(tableName = "book_table",
    foreignKeys = [ForeignKey(
        entity = AuthorEntity::class,
        parentColumns = ["author_id"],
        childColumns = ["book_author_id"],
        onDelete = CASCADE)],
    indices = [Index(value = ["book_author_id"])])
data class BookEntity(

    @ColumnInfo(name = "book_id")
    @PrimaryKey(autoGenerate = true)
    var book_id: Long = 0,

    @ColumnInfo(name = "book_title")
    var book_title : String,

    @ColumnInfo(name = "book_date")
    var book_date : String,

    @ColumnInfo(name = "book_image_url")
    var book_image_url : String? = null,

    @ColumnInfo(name = "book_author_id")
    var book_author_id : Long
) {
}