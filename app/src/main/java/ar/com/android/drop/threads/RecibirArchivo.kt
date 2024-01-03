package ar.com.android.drop.threads

import ar.com.android.drop.constants.Constants
import ar.com.android.drop.domine.GetFileMessage
import ar.com.android.drop.domine.GiveMeFile
import ar.com.android.drop.services.FileService
import java.io.FileOutputStream
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.ServerSocket
import java.net.Socket

class RecibirArchivo(fileService: FileService?) : Thread() {
    private  var fileService: FileService? = null
    private var socket: ServerSocket? = null

    init {
        this.fileService = fileService
    }

    override fun run() {
        try {
            socket = ServerSocket(
                Constants.FILE_PORT
            )
        } catch (e1: IOException) {
            //	JOptionPane.showMessageDialog(null, "Error con el socket");
            e1.printStackTrace()
        }
        while (true) {
            var fos: FileOutputStream
            var ois: ObjectInputStream
            var accept: Socket
            try {

                // Se abre el socket.
                accept = socket!!.accept()
                println(
                    "recibi un archivo de la ip"
                            + accept.inetAddress
                )
                //val fichero: String = fileService.obtenerNombreArchivo()
                val ficehro:String = "hola" //TODO borrar esto es solo para que compile
                // TODO

                // Se envia un mensaje de peticion de fichero.
                val oos = ObjectOutputStream(
                    accept.getOutputStream()
                )
                val mensaje = GiveMeFile()
                //mensaje.fileName = fichero TODO commentado para que compile
                oos.writeObject(mensaje)

                // Se abre un fichero para empezar a copiar lo que se reciba.
                fos = FileOutputStream(
                    fileService!!.directorioSalvado + "/"
                            + mensaje.fileName
                )

                // Se crea un ObjectInputStream del socket para leer los
                // mensajes
                // que contienen el fichero.
                ois = ObjectInputStream(accept.getInputStream())
                var mensajeRecibido: GetFileMessage
                var mensajeAux: Any
                do {
                    // Se lee el mensaje en una variabla auxiliar
                    mensajeAux = ois.readObject()

                    // Si es del tipo esperado, se trata
                    if (mensajeAux is GetFileMessage) {
                        mensajeRecibido = mensajeAux
                        // Se escribe en pantalla y en el fichero
                        print(
                            String(
                                mensajeRecibido.fileContent, 0,
                                mensajeRecibido.validBytes
                            )
                        )
                        fos.write(
                            mensajeRecibido.fileContent, 0,
                            mensajeRecibido.validBytes
                        )
                    } else {
                        // Si no es del tipo esperado, se marca error y se
                        // termina
                        // el bucle
                        System.err.println(
                            "Mensaje no esperado "
                                    + mensajeAux.javaClass.name
                        )
                        break
                    }
                } while (!mensajeRecibido.lastMessage)

                // Se cierra socket y fichero
                fos.close()
                ois.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
