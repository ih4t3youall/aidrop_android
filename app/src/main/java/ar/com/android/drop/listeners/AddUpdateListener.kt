package ar.com.android.drop.listeners

import android.content.Context
import android.view.View
import android.widget.TextView
import ar.com.android.drop.domine.ListPcAdapter
import ar.com.android.drop.domine.Pc

class AddUpdateListener (private val context: Context, private val pcName: TextView, private val pcIp: TextView,private val mListPcAdapter: ListPcAdapter) : View.OnClickListener {
    override fun onClick(view: View?) {
        // Your click handling logic
        val name = pcName.text.toString()
        val ip = pcIp.text.toString()

        if (!name.isBlank() && !ip.isBlank() && !name.equals("Name") && !pcIp.equals("Ip")) {
            // prepare model for use
            val model = Pc(ip, name)

            // add model to the adapter
            mListPcAdapter.addPc(model)

            // reset the input
            pcName.setText("Name")
            pcIp.setText("Ip")
        }
    }
}
