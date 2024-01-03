package ar.com.android.drop.services

import ar.com.android.drop.domine.Message
import ar.com.android.drop.exceptions.SendThroughtSocketException
import com.google.gson.Gson
import java.io.IOException
import java.io.ObjectOutputStream
import java.net.Socket

class SendMessage(var message: Message) : Thread() {
    override fun run() {
        var socket: Socket? = null
        try {
            //TODO ver que pasa aca que en debian trae localhost en ves de la ip
            socket = Socket(message.targetIp, PUERTO)
            val buffer = ObjectOutputStream(socket.getOutputStream())
            val messageJson = Gson().toJson(message)
            buffer.writeObject(messageJson)
        } catch (e: Exception) {
            val error = "Error con el socket al acceder al puerto : "
            try {
                throw SendThroughtSocketException(e, message.senderPc.ip, error)
            } catch (e1: SendThroughtSocketException) {
                e1.printStackTrace()
            }
        } finally {
            if (socket != null) {
                try {
                    socket.close()
                } catch (e: IOException) {
                    val error = "Error al cerrar el socket : "
                    try {
                        throw SendThroughtSocketException(
                            e, message.senderPc.ip,
                            error
                        )
                    } catch (e1: SendThroughtSocketException) {
                        e1.printStackTrace()
                    }
                }
            }
        }
    }



    companion object {
        private const val PUERTO = 8123
    }
}
