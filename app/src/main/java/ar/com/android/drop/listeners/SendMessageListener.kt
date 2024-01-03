package ar.com.android.drop.listeners

import android.view.View
import ar.com.android.drop.MainActivity
import ar.com.android.drop.domine.ListPcAdapter
import ar.com.android.drop.domine.Message
import ar.com.android.drop.services.SendMessage

class SendMessageListener(private val mListPcAdapter:ListPcAdapter, val textMessage:String,val mainActivity: MainActivity)  : View.OnClickListener {
    override fun onClick(v: View?) {
        //val editText = findViewById<EditText>(R.id.editTextTextPersonName)
        //val message = editText.text.toString()

        val pc = mListPcAdapter.getSelected()
        val message = Message(pc,"mensajePrompt",pc.ip, textMessage,"")
        SendMessage(message).start()
    }
}