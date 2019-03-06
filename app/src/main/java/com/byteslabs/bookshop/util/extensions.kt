package com.byteslabs.bookshop.util

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide

fun Any.lodInfo(message : String){
    Log.i(this.javaClass.simpleName, message)
}

fun Context.toastShort(text: String) = Toast.makeText(this, text, Toast.LENGTH_SHORT).show()

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible(){
    visibility = View.INVISIBLE
}

fun View.gone(){
    visibility = View.GONE
}

fun loadImageFormUrl(context: Context, imageUrl : String, imageView: ImageView ){
    Glide.with(context)
        .load(imageUrl)
        .centerCrop()
        .into(imageView)
}

fun loadImageFormUri(context: Context, imageUrl : Int, imageView: ImageView ){
    Glide.with(context)
        .load(imageUrl)
        .centerCrop()
        .into(imageView)
}