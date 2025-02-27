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

class StockInAdapter(private val listener: ItemClickListener) :
    RecyclerView.Adapter<StockInAdapter.StockInViewHolder>() {

    private val taskList: ArrayList<StockInModel> = arrayListOf()

    class StockInViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val serial: TextView = view.findViewById(R.id.serial_txt)
        val itemName: TextView = view.findViewById(R.id.item_txt)
        val currentStock: TextView = view.findViewById(R.id.current_stock_txt)
        val quantity: TextView = view.findViewById(R.id.quantity_txt)
        val expireDate: TextView = view.findViewById(R.id.expire_txt)
        val batchNo: TextView = view.findViewById(R.id.batch_txt)
        val receivedFrom: TextView = view.findViewById(R.id.received_from_txt)
        private val edit: ImageView = view.findViewById(R.id.edit_img)
        private val delete: ImageView = view.findViewById(R.id.delete_img)

        fun bind(item: StockInModel, listener: ItemClickListener, position: Int) {
            edit.setOnClickListener {
                listener.onEditClick(item, position)
            }

            delete.setOnClickListener {
                listener.onDeleteClick(item, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockInViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_stock_in, parent, false)
        return StockInViewHolder(view)
    }

    override fun onBindViewHolder(holder: StockInViewHolder, position: Int) {
        val item = taskList[position]

        holder.serial.text = "${position + 1}"
        holder.itemName.text = item.itemName
        holder.currentStock.text = "${item.currentStock}"
        holder.quantity.text = "${item.stockIn}"
        holder.expireDate.text = item.expireDate
        holder.batchNo.text = item.batchNo
        holder.receivedFrom.text = item.receiverFrom

        holder.bind(item, listener, holder.adapterPosition)
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

    interface ItemClickListener {
        fun onEditClick(item: StockInModel, position: Int)
        fun onDeleteClick(item: StockInModel, position: Int)
    }

}
