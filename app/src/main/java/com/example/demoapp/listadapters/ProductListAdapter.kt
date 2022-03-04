package com.example.demoapp.listadapters

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.example.demoapp.MainActivity
import com.example.demoapp.R
import com.example.demoapp.models.Product

class ProductListAdapter(
    private val items: List<Product>,
    private val fragment: Fragment
): RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ProductListAdapter.ViewHolder, position: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            holder.image.clipToOutline = true
        }
        holder.text.text = items[position].name
        Picasso
            .get()
            .load(items[position].imageURL)
            .placeholder(R.drawable.ic_image)
            .error(R.drawable.ic_broken_image)
            .into(holder.image)
        holder.itemView.setOnClickListener { _ ->
            ("https://example.com/items/${items[position].id}")?.toUri()?.let { uri ->
                val activity = (fragment.activity as? MainActivity)!!
                //activity.navigateToGraphContaining(uri)
                activity.navController.navigate(uri)
            }
        }

    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val image: ImageView by lazy { view.findViewById<ImageView>(R.id.listItemImage) }
        val text: TextView by lazy { view.findViewById<TextView>(R.id.listItemText) }
    }
}