package org.intelehealth.app.mpower.activities.stockManagement.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.openmrs.android_sdk.library.databases.entities.ProductModelEntity
import org.intelehealth.app.mpower.R

class ProductItemAdapter(private val listener: ItemClickListener) :
    RecyclerView.Adapter<ProductItemAdapter.StockInViewHolder>() {

    private val taskList: ArrayList<ProductModelEntity> = arrayListOf()

    class StockInViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemName: TextView = view.findViewById(R.id.tv_title)


        fun bind(item: ProductModelEntity, listener: ItemClickListener, position: Int) {
            itemName.setOnClickListener {
                listener.onItemClick(item, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockInViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.title_spinner_item, parent, false)
        return StockInViewHolder(view)
    }

    override fun onBindViewHolder(holder: StockInViewHolder, position: Int) {
        val item = taskList[position]
        holder.itemName.text = item.name

        holder.bind(item, listener, holder.adapterPosition)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateStockInList(newTasks: ArrayList<ProductModelEntity>) {
        taskList.clear()
        taskList.addAll(newTasks)
        notifyDataSetChanged()
    }

    interface ItemClickListener {
        fun onItemClick(item: ProductModelEntity, position: Int)
    }

}
