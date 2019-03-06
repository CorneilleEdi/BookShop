package com.byteslabs.bookshop.ui.view.author

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.byteslabs.bookshop.R
import com.byteslabs.bookshop.db.model.AuthorEntity

class AuthorListAdapter internal constructor(context: Context)
    :RecyclerView.Adapter<AuthorListAdapter.AuthorViewHolder>(){

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var authorsList = emptyList<AuthorEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AuthorViewHolder {
        val itemView = inflater.inflate(R.layout.author_list_item, parent, false)
        return AuthorViewHolder(itemView)
    }

    override fun getItemCount(): Int = authorsList.size

    override fun onBindViewHolder(holder: AuthorViewHolder, position: Int) {
        holder.bind(authorsList[position])
    }

    fun setAuthors(authors : List<AuthorEntity>){
        this.authorsList = authors
        notifyDataSetChanged()
    }

    private fun getDotColor(genre: String): String {
        var color = "#FAAB1A"
        when(genre) {
            "Drama" -> color = "#CB004C"
            "SciFy" -> color = "#091E42"
            "Romantic" -> color ="#FF0062"
            "Classic" -> color = "#FEDC00"
            "Action" -> color = "#253858"
            "Fable" -> color ="#FEB700"
            "Comic" -> color = "#35C4B2"
            "Crime" -> color = "#0052CC"
        }

        return color
    }


    interface OnItemClickListner {
        fun onItemClicked(author: AuthorEntity)
        fun onPopupClicked(view: View,author: AuthorEntity)
    }

    public fun setOnItemClickListner(listner: OnItemClickListner) {
        Companion.listner = listner
    }

    companion object {
        private var listner: OnItemClickListner? = null
    }


    inner class AuthorViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView)  {
        val author_name : TextView = itemView.findViewById(R.id.author_name_txt)
        val auhtor_dob : TextView = itemView.findViewById(R.id.author_dob_txt)
        val genre : TextView = itemView.findViewById(R.id.genre_dot_txt)
        private var overflow: ImageView = itemView.findViewById(R.id.overflow)

        private var dotBack: GradientDrawable = genre.background as GradientDrawable



        init {
            itemView.setOnClickListener {
                if (listner != null && adapterPosition != RecyclerView.NO_POSITION) {
                    listner!!.onItemClicked(authorsList[adapterPosition])

                }
            }

            overflow.setOnClickListener {
                listner!!.onPopupClicked(it,authorsList[adapterPosition])
            }

        }
        fun bind(author : AuthorEntity) {
            author_name.text = author.author_name
            auhtor_dob.text = author.author_dob
            var color: String = getDotColor(author.author_genre)

            dotBack.setColor(Color.parseColor(color))
            genre.text = author.author_genre[0].toString()
            genre.background = dotBack
        }
    }
}