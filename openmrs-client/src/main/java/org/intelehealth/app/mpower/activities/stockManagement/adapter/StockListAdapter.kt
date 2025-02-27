package org.intelehealth.app.mpower.activities.stockManagement.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.intelehealth.app.mpower.R
import com.openmrs.android_sdk.library.databases.entities.StockInModel

class StockListAdapter() :
    RecyclerView.Adapter<StockListAdapter.StockListViewHolder>() {

    private val taskList: ArrayList<StockInModel> = arrayListOf()

    class StockListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val serial: TextView = view.findViewById(R.id.serial_txt)
        val itemName: TextView = view.findViewById(R.id.item_txt)
        val quantity: TextView = view.findViewById(R.id.quantity_txt)
        val stockInDate: TextView = view.findViewById(R.id.stock_in_date_txt)
        val invoice: TextView = view.findViewById(R.id.invoice_txt)
        val syncStatus: TextView = view.findViewById(R.id.sync_txt)
        val provider: TextView = view.findViewById(R.id.provider_txt)

        fun bind(item: StockInModel, position: Int) {
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockListViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_stock_list, parent, false)
        return StockListViewHolder(view)
    }

    override fun onBindViewHolder(holder: StockListViewHolder, position: Int) {
        val item = taskList[position]

        holder.serial.text = "${position + 1}"
        holder.itemName.text = item.itemName
        holder.quantity.text = "${item.stockIn}"
        holder.stockInDate.text = item.stockInDate
        holder.invoice.text = item.invoice
        holder.provider.text = item.provider

        if (item.syncStatus == 0) holder.syncStatus.text = "Unsynced"
        else holder.provider.text = "Synced"


        holder.bind(item, holder.adapterPosition)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateStockInList(newTasks: ArrayList<StockInModel>) {
        taskList.clear()
        taskList.addAll(newTasks)

        notifyDataSetChanged()
    }


}
