package com.byteslabs.bookshop.ui.view.book

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.byteslabs.bookshop.R
import com.byteslabs.bookshop.db.model.BookEntity
import com.byteslabs.bookshop.util.loadImageFormUri
import com.byteslabs.bookshop.util.loadImageFormUrl

class BookListAdapter internal constructor(val context: Context)
    : RecyclerView.Adapter<BookListAdapter.BookViewHolder>(){


    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var bookList = emptyList<BookEntity>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val itemView = inflater.inflate(R.layout.book_list_item,parent,false)
        return BookViewHolder(itemView,context )
    }

    override fun getItemCount(): Int = bookList.size

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
            holder.bind(bookList[position])
    }


    fun setBooks(books : List<BookEntity>){
        this.bookList = books
        notifyDataSetChanged()
    }

    inner class BookViewHolder (itemView: View,private val context: Context) : RecyclerView.ViewHolder(itemView)  {

        val booktitle : TextView = itemView.findViewById(R.id.book_title_textView)
        val bookdate : TextView = itemView.findViewById(R.id.book_date_textView)
        val bookimage : ImageView = itemView.findViewById(R.id.book_imageView)

        fun bind(book : BookEntity){
            booktitle.text = book.book_title
            bookdate.text = book.book_date

            if (book.book_image_url !=null) {
                loadImageFormUrl(context, book.book_image_url!!,bookimage)
            }else{

            }

        }
    }
}