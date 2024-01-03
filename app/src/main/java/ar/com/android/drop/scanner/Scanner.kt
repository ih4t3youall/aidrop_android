package ar.com.android.drop.scanner

import ar.com.android.drop.domine.Message
import ar.com.android.drop.domine.Pc
import ar.com.android.drop.exceptions.SendThroughtSocketException
import ar.com.android.drop.services.PcService
import ar.com.android.drop.services.SendService
import java.util.LinkedList
import java.util.StringTokenizer

class Scanner(pcService: PcService?, sendService: SendService?) {
    var pcService: PcService? = null
    var sendService: SendService? = null

    init {
        this.pcService = pcService
        this.sendService = sendService
    }

    @Throws(InterruptedException::class)
    fun startScanning() {
        val ipToScan = cleanIp(pcService!!.obtenerIpLocal())
        for (i in 0..254) {
            val serverHostName = ipToScan + i
            val threadTestIp = ThreadScanner(
                serverHostName,
                pcs
            )
            threadTestIp.start()
        }
        Thread.sleep(7000)
        for (pc in pcs) {
            if (pc.ip != pcService!!.obtenerIpLocal()) {
                //val message = Message(
                //    pcService!!.pcLocal
                //) TODO comentado para que compile porque pcservice no existe todavia
                val message = Message(pc,"who",pc.ip, null, null)
                try {
                    sendService!!.sendMessage(message)
                } catch (e: SendThroughtSocketException) {
                    e.printStackTrace()
                }
            } else {
                println("omitiendo localhost...")
            }
        }
    }

    fun cleanIp(ip: String?): String {
        var cleanIp = ""
        val token = StringTokenizer(ip, ".")
        for (i in 0..2) {
            cleanIp += token.nextToken() + "."
        }
        return cleanIp
    }

    companion object {
        private val pcs = LinkedList<Pc>()
    }
}
