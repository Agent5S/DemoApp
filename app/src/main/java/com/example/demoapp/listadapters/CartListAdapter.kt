package com.example.demoapp.listadapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.example.demoapp.R
import com.example.demoapp.models.CartItem
import com.example.demoapp.models.Product
import com.example.demoapp.models.database
import com.example.demoapp.repositories.ProductRepository
import com.example.demoapp.repositories.RepositoryResult
import com.example.demoapp.ui.NumberStepper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CartListAdapter(val items: List<CartItem>, val fragment: Fragment): RecyclerView.Adapter<CartListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_cart_preview, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.image.clipToOutline = true
        ProductRepository.global.getProduct(items[position].productId).observe(fragment.viewLifecycleOwner, Observer {
            when (it) {
                is RepositoryResult.Error -> fragment.lifecycleScope.launch(Dispatchers.IO) {
                    database.cartItemDao().delete(items[position])
                }
                is RepositoryResult.Cached ->  it.value?.let {
                    holder.progressBar.visibility = View.VISIBLE
                    bindView(holder, items[position], it)
                }
                is RepositoryResult.Fresh -> it.value?.let {
                    holder.progressBar.visibility = View.INVISIBLE
                    bindView(holder, items[position], it)
                }
            }
        })
    }

    private fun bindView(holder: ViewHolder, cartItem: CartItem, product: Product) {
        holder.stepper.currentValue = cartItem.amount
        holder.name.text = product.name
        holder.price.text = "$0 each"
        holder.total.text = "$0"

        Picasso
            .get()
            .load(product.imageURL)
            .placeholder(R.drawable.ic_image)
            .error(R.drawable.ic_broken_image)
            .into(holder.image)

        holder.stepper.setOnValueChangeListener {
            fragment.lifecycleScope.launch(Dispatchers.IO) {
                if (it == 0) {
                    database.cartItemDao().delete(cartItem)
                    return@launch
                }

                database.cartItemDao().insert(CartItem(cartItem.productId, it))
            }
        }
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val progressBar by lazy { view.findViewById<ProgressBar>(R.id.cartItemProgressBar) }
        val stepper by lazy { view.findViewById<NumberStepper>(R.id.cartItemStepper) }
        val image by lazy { view.findViewById<ImageView>(R.id.cartItemImage) }
        val name  by lazy { view.findViewById<TextView>(R.id.cartItemName) }
        val price  by lazy { view.findViewById<TextView>(R.id.cartItemPrice) }
        val total by lazy { view.findViewById<TextView>(R.id.cartItemTotal) }
    }
}