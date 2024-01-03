package ar.com.android.drop

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import ar.com.android.drop.domine.Message
import ar.com.android.drop.domine.Pc
import ar.com.android.drop.services.SendMessage
import com.google.gson.Gson

class SendActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.send_text)

        val sendButton = findViewById<Button>(R.id.send_button)
        sendButton.setOnClickListener {
            val pc = Gson().fromJson(intent.getStringExtra("localPc"), Pc::class.java)
            val command = intent.getStringExtra("command")
            val targetIp = intent.getStringExtra("targetIp")
            val textMessage = findViewById<EditText>(R.id.text_to_send).text.toString()
            val message = Message(pc, command!!, targetIp!!, textMessage,null)
            SendMessage(message)
            finish()

        }

    }
}