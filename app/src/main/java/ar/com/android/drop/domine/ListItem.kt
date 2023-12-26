package ar.com.android.drop.domine

class listItem(val name: String, val ip:String) {

    companion object {
        private var lastContactId = 0
        fun createContactsList(numContacts: Int) : ArrayList<Pc> {
            val pcs = ArrayList<Pc>()
            for (i in 1..numContacts) {
                val pc = Pc("192.168.0.1")
                pc.pcName = "a pc"
                pcs.add(pc)
            }
            return pcs
        }
    }
}