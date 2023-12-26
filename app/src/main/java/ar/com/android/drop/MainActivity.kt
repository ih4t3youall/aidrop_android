package ar.com.android.drop

import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ar.com.android.drop.domine.ListPcAdapter
import ar.com.android.drop.domine.Pc
import ar.com.android.drop.listeners.OnPcClickListener
import ar.com.android.drop.scanner.Scanner
import ar.com.android.drop.services.IpService
import ar.com.android.drop.services.PcService
import ar.com.android.drop.services.SendService
import java.util.Stack


class MainActivity : AppCompatActivity() {


    private lateinit var pcName: EditText
    private lateinit var pcIp: EditText
    private lateinit var mListPcAdapter: ListPcAdapter
    private var modelToBeUpdated: Stack<Pc> = Stack()
    private lateinit var addPc: Button


    private val mOnPcClickListener = object : OnPcClickListener {
        override fun onUpdate(position: Int, model: Pc) {

            // we want to update
            modelToBeUpdated.add(model)

            // set the value of the clicked item in the edit text
            pcName.setText(model.pcName)
            pcIp.setText(model.ip)
        }

        override fun onDelete(model: Pc) {
            mListPcAdapter.removeProduct(model)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val listView = findViewById<RecyclerView>(R.id.list_view)

        // initialize the recycler view
        val listView = findViewById<RecyclerView>(R.id.list_view)
        listView.layoutManager = LinearLayoutManager(this)
        listView.setHasFixedSize(true)

        pcName = findViewById(R.id.main_pc_name)
        pcIp = findViewById(R.id.main_pc_ip)
        mListPcAdapter = ListPcAdapter(this, mOnPcClickListener)
        listView.adapter = mListPcAdapter
        mListPcAdapter.addPc(Pc("192.168.0.2","martin"))
        // we will be adding adapter here later

        addPc = findViewById(R.id.add_pc)
        addPc.setOnClickListener {

            val name = pcName.text.toString()
            val ip = pcIp.text.toString()

            if (!name.isBlank() && !ip.isBlank()) {


                // prepare model for use
                val model = Pc(name, ip)

                // add model to the adapter
                mListPcAdapter.addPc(model)

                // reset the input
                pcName.setText("")
                pcIp.setText("")
            }
        }





    }

   private fun scanerr(){
       val buttonClick = findViewById<Button>(R.id.scan_button)
       buttonClick.setOnClickListener {
           val pcService = PcService()
           val sendService = SendService()
           val ipService = IpService()
           if (Build.VERSION.SDK_INT > 9) {
               val policy = ThreadPolicy.Builder().permitAll().build()
               StrictMode.setThreadPolicy(policy)
               val pc = ipService.localIp
               pcService.pcLocal=pc
           }
           Scanner(pcService, sendService).startScanning()
       }
   }
}