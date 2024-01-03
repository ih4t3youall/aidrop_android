package ar.com.android.drop.services

import ar.com.android.drop.domine.Message

class FileService {
    private var message: Message? = null
    @JvmField
	var directorioSalvado = ""
    fun archivoAEnviar(message: Message?) {
        this.message = message
    }

    fun obtenerArchivoAEviar(): Message? {
        return message
    }

    //fun obtenerNombreArchivo(): String {
    //    return message.getFile().getName()
    //}

    fun archivoARecibir(message: Message?) {
        this.message = message
    }
}
