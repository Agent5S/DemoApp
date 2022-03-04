package com.example.demoapp.listadapters

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.core.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.example.demoapp.MainActivity
import com.example.demoapp.R
import com.example.demoapp.models.Featured

class PreviewGroupAdapter(
    private var featured: List<Featured>,
    private val fragment: Fragment
): RecyclerView.Adapter<PreviewGroupAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_featured_item, parent, false)
        return ViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return featured.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position == 0) {
            val margin: Int
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                margin = holder.view.marginEnd
                holder.view.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                    this.updateMarginsRelative(start = margin)
                }
            } else {
                margin = holder.view.marginRight
                holder.view.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                    this.updateMargins(left = margin)
                }
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            holder.view.clipToOutline = true
        }
        holder.text.text = featured[position].name
        Picasso
            .get()
            .load(featured[position].imageURL)
            .placeholder(R.drawable.ic_image)
            .error(R.drawable.ic_broken_image)
            .into(holder.image)
        holder.view.setOnClickListener { _ ->
            featured[position].deepLink?.toUri()?.let { uri ->
                val activity = (fragment.activity as? MainActivity)!!
                activity.navigateToGraphContaining(uri)
                val a = activity.navController
                activity.navController.navigate(uri)
            }
        }
    }

    class ViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        val text: TextView by lazy { view.findViewById<TextView>(R.id.featuredItemText) }
        val image: ImageView by lazy { view.findViewById<ImageView>(R.id.featuredItemImage) }
    }

}