package com.byteslabs.bookshop.ui.view.book

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.byteslabs.bookshop.R
import com.byteslabs.bookshop.db.model.BookEntity
import com.byteslabs.bookshop.util.lodInfo
import java.lang.Exception

class BookDialogFragment : DialogFragment() {
    private lateinit var onInputListener : BookOnInputListener
    private lateinit var title : TextView
    private lateinit var btnSave : Button
    private lateinit var btnCancel : Button
    private lateinit var bookNameEditText: EditText
    private lateinit var bookDateEditText: EditText
    private lateinit var bookImageEditText: EditText

    public interface  BookOnInputListener{
        fun save(book : BookEntity)
        fun  update(book : BookEntity)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view : View = inflater.inflate(R.layout.book_dialog_fragment,container,false)

        val  author_id : Long = arguments!!.getLong("author_id",0)
        title = view.findViewById(R.id.book_dialog_title)
        btnSave = view.findViewById(R.id.save_book_btn)
        btnCancel = view.findViewById(R.id.cancel_book_btn)
        bookNameEditText = view.findViewById(R.id.book_title_editText)
        bookDateEditText = view.findViewById(R.id.book_date_editText)
        bookImageEditText = view.findViewById(R.id.book_image_editText)

        btnCancel.setOnClickListener {
            dialog!!.dismiss()
        }


        btnSave.setOnClickListener {
            val name = bookNameEditText.text.toString().trim()
            val date = bookDateEditText.text.toString().trim()

            var imageUrl : String? = ""
            try {
                imageUrl = bookImageEditText.text.toString().trim()
            }catch (e: Exception){
                imageUrl = null
            }


            if (name.isNullOrBlank() || date.isNullOrBlank()){
                dialog!!.dismiss()
            }

            val book = BookEntity(
                book_author_id = author_id,
                book_title = name,
                book_date = date,
                book_image_url = imageUrl
            )


            onInputListener.save(book)
            dialog!!.dismiss()
        }



        return view
    }

    companion object {
        fun newInstance(author_id : Long/*shouldUpdate: Boolean,book: BookEntity*/): BookDialogFragment {
            val frag = BookDialogFragment()
            val args = Bundle()
            args.putLong("author_id", author_id )
            frag.arguments = args
            return frag
        }

        private val TAG = "AddDialog"

    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            onInputListener = (activity as BookOnInputListener?)!!
        }catch (e : ClassCastException){
            lodInfo("onAttach: ClassCastException: " + e.message );
        }
    }
}