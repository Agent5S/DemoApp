package com.example.demoapp.listadapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.demoapp.R
import com.example.demoapp.models.FeaturedList

class PreviewListAdapter(
    private var rows: List<FeaturedList>,
    private val fragment: Fragment
): RecyclerView.Adapter<PreviewListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_featured_row, parent, false)
        return ViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return  rows.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.text.text = rows[position].title
        holder.rowRecycler.adapter =
            PreviewGroupAdapter(
                rows[position].items,
                fragment
            )
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val text: TextView by lazy { itemView.findViewById<TextView>(R.id.featuredRowTitle) }
        val rowRecycler: RecyclerView by lazy { itemView.findViewById<RecyclerView>(
            R.id.featuredRowRecycler
        ) }
    }
}