package ar.com.android.drop

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ar.com.android.drop.devHelpers.ScreenLog
import ar.com.android.drop.domine.ListPcAdapter
import ar.com.android.drop.domine.Message
import ar.com.android.drop.domine.Pc
import ar.com.android.drop.listeners.AddUpdateListener
import ar.com.android.drop.listeners.OnPcClickListener
import ar.com.android.drop.listeners.SendMessageListener
import ar.com.android.drop.services.IpService
import ar.com.android.drop.services.PcService
import ar.com.android.drop.services.SendService
import ar.com.android.drop.threads.ReceiveMessage
import com.google.gson.Gson
import java.util.Stack


class MainActivity : AppCompatActivity() {


    private lateinit var pcName: EditText
    private lateinit var pcIp: EditText
    private lateinit var mListPcAdapter: ListPcAdapter
    private var modelToBeUpdated: Stack<Pc> = Stack()
    private lateinit var addPc: Button
    private lateinit var sendMessage: Button
    private lateinit var ipService :IpService
    lateinit var myPc:Pc
    lateinit var screenlog: ScreenLog
    lateinit var receiveMessage:ReceiveMessage
    lateinit var pcService:PcService


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
        screenlog = ScreenLog(findViewById<TextView>(R.id.showText))
        pcService = PcService()
        ipService = IpService(this, screenlog, pcService)
        val sendService = SendService()
        ipService.start()
        //val listView = findViewById<RecyclerView>(R.id.list_view)

        // initialize the recycler view
        val listView = findViewById<RecyclerView>(R.id.list_view)
        listView.layoutManager = LinearLayoutManager(this)
        listView.setHasFixedSize(true)

        pcName = findViewById(R.id.main_pc_name)
        pcIp = findViewById(R.id.main_pc_ip)
        val receivedTextField:EditText = findViewById(R.id.result_text_view)
        mListPcAdapter = ListPcAdapter(this, mOnPcClickListener)
        listView.adapter = mListPcAdapter
        receiveMessage = ReceiveMessage(sendService, screenlog, pcService, mListPcAdapter, receivedTextField)

        //inicia el servidor de recepcion de mensajes
        initServer()

        addPc = findViewById(R.id.add_update)
        addPc.setOnClickListener(AddUpdateListener(this, pcName, pcIp, mListPcAdapter))

        //listeners
        findViewById<Button>(R.id.send_text).setOnClickListener {
            val intent = Intent(this, SendActivity::class.java).apply {
                val message = Message(pcService.pcLocal,"mensajePrompt",mListPcAdapter.getSelected().ip,null,null)
                putExtra("localPc", Gson().toJson(pcService.pcLocal))
                putExtra("command", "mensajePrompt")
                putExtra("targetIp", mListPcAdapter.getSelected().ip)
            }
            startActivity(intent)
        }





        findViewById<Button>(R.id.edit_local).setOnClickListener {
            screenlog.addText("martin")
            screenlog.addText("y")
            screenlog.addText("meke")
        }
    }

    fun initServer(){
        receiveMessage.start()
    }
   //private fun scanerr(){
   //    val buttonClick = findViewById<Button>(R.id.scan_button)
   //    buttonClick.setOnClickListener {
   //        val pcService = PcService()
   //        val sendService = SendService()
   //        val ipService = IpService()
   //        if (Build.VERSION.SDK_INT > 9) {
   //            val policy = ThreadPolicy.Builder().permitAll().build()
   //            StrictMode.setThreadPolicy(policy)
   //            val pc = ipService.localIp
   //            pcService.pcLocal=pc
   //        }
   //        Scanner(pcService, sendService).startScanning()
   //    }
   //}
}