package ar.com.android.drop.listeners

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast

class AddUpdateListener (private val context: Context) : View.OnClickListener {
    override fun onClick(view: View?) {
        // Your click handling logic
        Log.d("InfoTag", "my log: ")

        Toast.makeText(context, "Button Clicked!", Toast.LENGTH_SHORT).show()
    }
}
