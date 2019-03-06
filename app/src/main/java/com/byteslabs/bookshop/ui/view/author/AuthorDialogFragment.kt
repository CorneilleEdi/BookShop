package com.byteslabs.bookshop.ui.view.author

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.byteslabs.bookshop.R
import com.byteslabs.bookshop.db.model.AuthorEntity
import com.byteslabs.bookshop.util.lodInfo
import java.lang.ClassCastException

class AuthorDialogFragment : DialogFragment() {

    private lateinit var title : TextView
    private lateinit var btnSave : Button
    private lateinit var btnCancel : Button
    private lateinit var auhtorGenreSpinner: Spinner
    private lateinit var authorNameEditText: EditText
    private lateinit var auhtorDobEditText: EditText

    private lateinit var onInputListener : OnInputListener

    var authorEntity : AuthorEntity? = null


    public interface  OnInputListener{
        fun save(author: AuthorEntity)
        fun  update(author: AuthorEntity)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val shouldUpdate : Boolean  = arguments!!.getBoolean("update")

        val view : View = inflater.inflate(R.layout.author_dialog_fragment,container,false)

        title = view.findViewById(R.id.dialog_title)
        authorNameEditText = view.findViewById(R.id.author_name_editText)
        auhtorDobEditText = view.findViewById(R.id.author_dob_editText)
        auhtorGenreSpinner = view.findViewById(R.id.author_genre_spinner)
        btnSave = view.findViewById(R.id.save_author_btn)
        btnCancel = view.findViewById(R.id.cancel_author_btn)



        if (shouldUpdate){
            title.text = getString(R.string.update_note_title)
            btnSave.text = getString(R.string.update)
        }

        if (authorEntity!=null){
            authorNameEditText.setText(authorEntity!!.author_name, TextView.BufferType.EDITABLE);
            auhtorDobEditText.setText(authorEntity!!.author_dob)
        }



        btnCancel.setOnClickListener {
            dialog!!.dismiss()
        }
        btnSave.setOnClickListener {
            val name = authorNameEditText.text.toString().trim()
            val dob = auhtorDobEditText.text.toString().trim()
            val genre = auhtorGenreSpinner.selectedItem.toString()

            if (name.isNullOrBlank() || dob.isNullOrBlank()){
                dialog!!.dismiss()
            }

            val author : AuthorEntity = AuthorEntity(
                author_name = name,
                author_dob = dob,
                author_genre = genre
            )

            if (shouldUpdate) onInputListener.update(author)
            else onInputListener.save(author)

            dialog!!.dismiss()
        }


        return view
    }



    companion object {
        fun newInstance(shouldUpdate: Boolean,author: AuthorEntity?): AuthorDialogFragment {
            val frag = AuthorDialogFragment()
            val args = Bundle()
            args.putBoolean("update", shouldUpdate )
            frag.authorEntity = author
            frag.arguments = args
            return frag
        }

        private val TAG = "AddDialog"

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            onInputListener = (activity as OnInputListener?)!!
        }catch (e : ClassCastException){
            lodInfo("onAttach: ClassCastException: " + e.message );
        }
    }
}