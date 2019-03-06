package com.byteslabs.bookshop.ui.view.book

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.byteslabs.bookshop.R
import com.byteslabs.bookshop.db.model.BookEntity
import com.byteslabs.bookshop.ui.viewmodel.BookViewModel
import com.byteslabs.bookshop.util.invisible
import com.byteslabs.bookshop.util.lodInfo
import com.byteslabs.bookshop.util.visible

import kotlinx.android.synthetic.main.activity_book.*

class BookActivity : AppCompatActivity() , BookDialogFragment.BookOnInputListener  {


    private var author_id  : Long = 0
    private lateinit var bookViewModel: BookViewModel
    private lateinit var bookLiveData: LiveData<List<BookEntity>>
    private lateinit var noBookTxt : TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: BookListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book)

        setSupportActionBar(toolbar)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        initComponents()

        author_id = intent.getLongExtra("author_id",0)

        bookLiveData = bookViewModel.getAllBookFromAuthor(author_id)

        bookLiveData.observe(this, Observer { booksOfAuthor ->
            //lodInfo(booksOfAuthor.size.toString())
            if (booksOfAuthor.isEmpty()){
                recyclerView.invisible()
                noBookTxt.visible()
            }else{
                noBookTxt.invisible()
                recyclerView.visible()

                booksOfAuthor?.let {
                    lodInfo(it.size.toString())
                    adapter.setBooks(it)
                }
            }
        })


        book_fab.setOnClickListener { view ->
            showDialog()
        }

    }

    override fun save(book: BookEntity) {
        bookViewModel.insert(book)
    }

    override fun update(book: BookEntity) {

    }

    private fun initComponents(){
        recyclerView = findViewById<RecyclerView>(R.id.bookRecyclerView)
        adapter = BookListAdapter(this)
        noBookTxt = findViewById(R.id.no_book_txt)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        bookViewModel = ViewModelProviders.of(this,BookViewModel.Factory(application)).get(BookViewModel::class.java)
    }

    private fun showDialog(){
        val dialogFragment : BookDialogFragment =
            BookDialogFragment.newInstance(author_id)
        dialogFragment.show(supportFragmentManager,"book_dialog")
    }

}
