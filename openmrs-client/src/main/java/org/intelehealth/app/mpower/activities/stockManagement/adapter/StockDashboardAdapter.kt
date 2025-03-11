package org.intelehealth.app.mpower.activities.stockManagement.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.openmrs.android_sdk.library.databases.entities.StockDashboardModelEntity
import com.openmrs.android_sdk.library.databases.entities.StockListModelEntity
import org.intelehealth.app.mpower.R

class StockDashboardAdapter() :
    RecyclerView.Adapter<StockDashboardAdapter.StockListViewHolder>() {

    private val taskList: ArrayList<StockDashboardModelEntity> = arrayListOf()

    class StockListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val serial: TextView = view.findViewById(R.id.serial_txt)
        val itemName: TextView = view.findViewById(R.id.item_txt)
        val currentStock: TextView = view.findViewById(R.id.current_stock_txt)
        val stockIn: TextView = view.findViewById(R.id.stock_in_txt)
        val stockOut: TextView = view.findViewById(R.id.stock_out_txt)
        val stockDiff: TextView = view.findViewById(R.id.stock_diff_txt)

        fun bind(item: StockDashboardModelEntity, position: Int) {
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockListViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_stock_dashboard, parent, false)
        return StockListViewHolder(view)
    }

    override fun onBindViewHolder(holder: StockListViewHolder, position: Int) {
        val item = taskList[position]

        holder.serial.text = "${position + 1}"
        holder.itemName.text = item.name
        holder.currentStock.text = "${item.currentstock}"
        holder.stockIn.text = "${item.stockin}"
        holder.stockOut.text = "${item.stockout}"
        holder.stockDiff.text = "${item.adjust_amount}"


        holder.bind(item, holder.adapterPosition)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateStockInList(newTasks: ArrayList<StockDashboardModelEntity>) {
        taskList.clear()
        taskList.addAll(newTasks)

        notifyDataSetChanged()
    }


}
