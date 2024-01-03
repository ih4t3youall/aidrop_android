package ar.com.android.drop.domine


open class Message( val senderPc:Pc,  val command:String,  val targetIp:String, val payload:String?, val payloadType:String?) {

}