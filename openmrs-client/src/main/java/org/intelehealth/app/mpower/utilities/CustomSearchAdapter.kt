package org.intelehealth.app.mpower.utilities

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import com.openmrs.android_sdk.library.databases.entities.ProductModelEntity
import org.intelehealth.app.mpower.R

class CustomSearchAdapter(
    context: Context,
    private val items: List<ProductModelEntity>
) : ArrayAdapter<ProductModelEntity>(context, 0, items), Filterable {

    private var filteredList: List<ProductModelEntity> = items

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createView(position, convertView, parent)
    }

    private fun createView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.title_spinner_item, parent, false)

        val item = items[position]
        val text = view.findViewById<TextView>(R.id.tv_title)
        text.text = item.name

        return view
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val query = constraint?.toString()?.lowercase()?.trim()

                val results = FilterResults()
                results.values = if (query.isNullOrEmpty()) {
                    items  // Show full list if no search query
                } else {
                    items.filter { it.name!!.lowercase().contains(query) } // Filter by name
                }
                results.count = (results.values as List<*>).size
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredList = results?.values as List<ProductModelEntity>  // Update filtered list
                notifyDataSetChanged()  // Refresh adapter
            }
        }
    }
}