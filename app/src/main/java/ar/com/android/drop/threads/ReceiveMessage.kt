package ar.com.android.drop.threads

import android.util.Log
import android.widget.EditText
import ar.com.android.drop.MainActivity
import ar.com.android.drop.constants.Constants
import ar.com.android.drop.devHelpers.ScreenLog
import ar.com.android.drop.domine.ListPcAdapter
import ar.com.android.drop.domine.Message
import ar.com.android.drop.services.PcService
import ar.com.android.drop.services.SendService
import com.google.gson.Gson
import java.io.IOException
import java.io.ObjectInputStream
import java.net.ServerSocket

class ReceiveMessage(
    private val sendService: SendService,
    private val screenLog: ScreenLog,
    private val pcService: PcService,
    private val mListAdapter: ListPcAdapter,
    private val responseTextEdit: EditText
) : Thread() {
    private val context: MainActivity? = null

    override fun run() {
        while (true) {
            lateinit var socket: ServerSocket
            try {
                Log.d("loginfo", "Waiting for send....")

                socket = ServerSocket(PORT)
                val cliente = socket.accept()
                val ipOtroCliente = cliente.inetAddress
                    .hostAddress

                Log.d("loginfo", "Connected with $ipOtroCliente")
                Log.d("InfoTag", "ip otro cliente cliente: " + cliente.inetAddress)

                //	cliente.setSoLinger(true, 10);
                val buffer = ObjectInputStream(
                    cliente.getInputStream()
                )

                //Mensaje mensaje = (Mensaje) buffer.readObject();
                val json = buffer.readObject() as String
                val message = Gson().fromJson(json, Message::class.java)

                if (message.command.equals("who")) {
                    pcService.addExternalPc(message.senderPc)
                    //Message responseMessage = new Message(
                    //		pcService.getPcLocal());
                    val responseMessage = Message(pcService.pcLocal,"autenticar",
                        ipOtroCliente as String,"","")
                    screenLog.addText("ipOtroCliente"+ipOtroCliente)
                    sendService.sendMessage(responseMessage)
                    mListAdapter.addPc(message.senderPc)
                }
                if (message.command.equals("autenticar")) {
                    Log.d(
                        "loginfo", " Pc received from IP: "
                                + ipOtroCliente
                    )
                    val pc = message.senderPc
                    pcService.addExternalPc(pc)
                }
                if (message.command.equals("mensajePrompt")) {
                    Log.d("loginfo", "************************************************")
                    Log.d("loginfo", "*********Message received******************")
                    Log.d("loginfo", "************************************************")
                    Log.d("loginfo", message.payload!!)
                    screenLog.addText(message.payload)
                    responseTextEdit.setText(message.payload)
                    Log.d("loginfo", "************************************************")
                    Log.d("loginfo", "*********End message received ****************************")
                    Log.d("loginfo", "************************************************")
                }
            } catch (e: Exception) {
                val sb = StringBuilder()
                sb.append("errorCause: ").append(e.cause)
                    .append(" errorMessage: ")
                    .append(e.message)
                val error = ("Error whit serversocket: "
                        + PORT)
                Log.d("InfoTag", "error log:$error")
                Log.d("InfoTag", "stackTrace log:" + e.message)
                break
            } finally {
                try {
                    socket.close()
                } catch (e: IOException) {
                    System.err.println("Error closing the serversocket.")
                }
            }
        }
    }


    companion object {
        private const val PORT = Constants.PORT
    }
}
