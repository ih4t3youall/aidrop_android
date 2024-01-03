package ar.com.android.drop.devHelpers

import android.widget.TextView

class ScreenLog(private val textView:TextView) {

    fun addText(text:String){
        val newText = StringBuilder(textView.text).append(text).append("  ||  ").toString()
        textView.text = newText
    }

    fun clear(){
        textView.setText("")
    }
}