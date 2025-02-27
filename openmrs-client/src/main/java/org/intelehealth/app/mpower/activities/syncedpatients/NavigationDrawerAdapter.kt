package org.intelehealth.app.mpower.activities.syncedpatients

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.intelehealth.app.mpower.R
import com.openmrs.android_sdk.library.databases.entities.StockInModel
import org.intelehealth.app.mpower.listeners.ItemClickListener
import org.intelehealth.app.mpower.models.NavDrawerItem

class NavigationDrawerAdapter(
    private var navItems: List<NavDrawerItem>,
    private val onItemClick: (NavDrawerItem) -> Unit
) : RecyclerView.Adapter<NavigationDrawerAdapter.NavigationViewHolder>() {


    class NavigationViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title_txt)
        val arrow: AppCompatImageView = view.findViewById(R.id.img_arrow)
        val subMenuRecycle: RecyclerView = view.findViewById(R.id.drawer_item_list)

        fun bind(item: NavDrawerItem) {
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NavigationViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_nav_drawer_item, parent, false)
        return NavigationViewHolder(view)
    }

    override fun onBindViewHolder(holder: NavigationViewHolder, position: Int) {

        val item = navItems[position]

        holder.title.text = item.title

        if (item.subMenuItems.isNullOrEmpty()) {
            holder.arrow.visibility = View.GONE
        } else {
            holder.arrow.visibility = View.VISIBLE
            holder.arrow.rotation = if (item.isExpanded) 180f else 0f
        }
        holder.subMenuRecycle.visibility = if (item.isExpanded) View.VISIBLE else View.GONE

        // Set up sub-menu RecyclerView
        if (!item.subMenuItems.isNullOrEmpty()) {
            holder.subMenuRecycle.layoutManager = LinearLayoutManager(holder.itemView.context)
            holder.subMenuRecycle.adapter = NavigationDrawerAdapter(item.subMenuItems) {
                onItemClick(it) // Handle sub-menu clicks
            }
        }

        // Click Listener for Parent Menu
        holder.itemView.setOnClickListener {
            if (!item.subMenuItems.isNullOrEmpty()) {
                item.isExpanded = !item.isExpanded
                notifyItemChanged(holder.adapterPosition)
            } else {
                onItemClick(item) // Handle click
            }
        }
        holder.bind(navItems[position])
    }

    override fun getItemCount(): Int {
        return navItems.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateModuleItems(modules: List<NavDrawerItem>) {
        navItems = modules
        notifyDataSetChanged()
    }


}
