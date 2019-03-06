package com.byteslabs.bookshop.ui.view.author

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.byteslabs.bookshop.R
import com.byteslabs.bookshop.db.model.AuthorEntity
import com.byteslabs.bookshop.ui.view.book.BookActivity
import com.byteslabs.bookshop.ui.viewmodel.AuthorViewModel
import com.byteslabs.bookshop.util.invisible
import com.byteslabs.bookshop.util.visible

import kotlinx.android.synthetic.main.activity_author.*

class AuthorActivity : AppCompatActivity(), AuthorDialogFragment.OnInputListener {


    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AuthorListAdapter
    private lateinit var authorViewModel: AuthorViewModel
    private lateinit var noAuthorTxt : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_author)
        setSupportActionBar(toolbar)


        initComponent()

        authorViewModel.allAuthor.observe(this, Observer { authors ->
            if (authors.isEmpty()){
                noAuthorTxt.visible()
                recyclerView.invisible()
            }else{
                authors?.let {
                    noAuthorTxt.invisible()
                    recyclerView.visible()
                    adapter.setAuthors(it)
                }
            }
        })

        fab.setOnClickListener { _ ->
            showDialog(false,null)
            /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()*/
        }
    }

    private fun initComponent(){
        recyclerView = findViewById<RecyclerView>(R.id.authorRecyclerView)
        noAuthorTxt = findViewById(R.id.no_author_txt)
        adapter = AuthorListAdapter(this)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)


        authorViewModel = ViewModelProviders.of(this, AuthorViewModel.Factory(application)).get(
            AuthorViewModel::class.java)


        adapter.setOnItemClickListner(object :
            AuthorListAdapter.OnItemClickListner {
            override fun onItemClicked(author: AuthorEntity) {

                val intent = Intent(this@AuthorActivity, BookActivity::class.java)
                intent.putExtra("author_id",author.author_id)
                startActivity(intent)

            }

            override fun onPopupClicked(view: View, author: AuthorEntity) {
                showPopupMenu(view,author)
            }

        })
    }


    private fun showDialog(shouldUpdate: Boolean, author: AuthorEntity?){
        val dialogFragment : AuthorDialogFragment =
            AuthorDialogFragment.newInstance(shouldUpdate, author)
        dialogFragment.show(supportFragmentManager,"author_dialog")
    }

    override fun save(author : AuthorEntity) {
        authorViewModel.insert(author)
    }

    override fun update(author : AuthorEntity) {
        authorViewModel.update(author)
    }



    inner class MyMenuItemClickListener( private val author: AuthorEntity) : PopupMenu.OnMenuItemClickListener {
        override fun onMenuItemClick(menuItem: MenuItem): Boolean {
            when (menuItem.itemId) {
                R.id.action_delete -> {
                    authorViewModel.delete(author)
                    return true
                }
                R.id.action_update -> {
                    showDialog(true,author)
                    return true
                }
            }
            return false
        }
    }


    private fun showPopupMenu(view: View, author: AuthorEntity) {
        // inflate menu
        val popup = PopupMenu(this, view)
        val inflater = popup.menuInflater
        inflater.inflate(R.menu.item_menu, popup.menu)
        popup.setOnMenuItemClickListener(MyMenuItemClickListener(author))
        popup.show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_deleteAll -> {
                authorViewModel.deleteAllAuthor()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
