package org.intelehealth.app.mpower.utilities

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.openmrs.android_sdk.library.databases.entities.ProductModelEntity
import org.intelehealth.app.mpower.R

class CustomSpinnerAdapter(
    context: Context,
    private val items: List<ProductModelEntity>
) : ArrayAdapter<ProductModelEntity>(context, 0, items) {

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
}