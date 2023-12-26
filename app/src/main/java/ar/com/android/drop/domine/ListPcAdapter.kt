package ar.com.android.drop.domine

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ar.com.android.drop.R
import ar.com.android.drop.listeners.OnPcClickListener
import android.util.Log


class ListPcAdapter(private val mContext: Context,
                    private val mOnPcClickListener: OnPcClickListener,
                    private val mPcList: ArrayList<Pc> = ArrayList()
) : RecyclerView.Adapter<ListPcAdapter.PcViewHolder>() {

    /**
     * ViewHolder implementation for holding the mapped views.
     */
    inner class PcViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pcName: TextView = itemView.findViewById(R.id.pc_name)
        val pcIp: TextView = itemView.findViewById(R.id.pc_ip)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PcViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.pc_list, parent, false)
        val holder = PcViewHolder(view)

        // item view is the root view for each row
        holder.itemView.setOnClickListener {

            // adapterPosition give the actual position of the item in the RecyclerView
            val position = holder.adapterPosition
            val model = mPcList[position]

            mOnPcClickListener.onUpdate(position, model)
        }

        return holder
    }
    fun addPc(pc: Pc) {
        mPcList.add(pc)
        // notifyDataSetChanged() // this method is costly I avoid it whenever possible
        notifyItemInserted(mPcList.size)
    }

    override fun getItemCount(): Int {
        return mPcList.size
    }

    fun removeProduct(model: Pc) {
        //TODO not implemented
    //    val position = mProductList.indexOf(model)
    //    mProductList.remove(model)
    //    notifyItemRemoved(position)
    }
    fun updateProduct(model: Pc?) {

        //TODO not implemented
        if (model == null) return // we cannot update the value because it is null

        for (item in mPcList) {
            // search by id
            if (item.pcName.equals(model.pcName)) {
                val position = mPcList.indexOf(model)
                mPcList[position] = model
                notifyItemChanged(position)
                break // we don't need to continue any more iterations
            }
        }
    }

    override fun onBindViewHolder(holder: PcViewHolder, position: Int) {
        // data will be set here whenever the system thinks it's required

        // get the product at position
        val pc = mPcList[position]
        Log.i("InfoTag","mPcList[$position]: ${pc.pcName}, ${pc.ip}")

        for (pc in mPcList)
            Log.i("InfoTag", "pcList: ${pc.pcName}")
        holder.pcName.text = pc.pcName
        holder.pcIp.text = "${pc.ip}"
    }

}