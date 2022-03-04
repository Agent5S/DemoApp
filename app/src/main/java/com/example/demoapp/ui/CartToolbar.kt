package com.example.demoapp.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.demoapp.R
import com.example.demoapp.databinding.ViewCartToolbarBinding
import com.example.demoapp.models.CartItem

class CartToolbar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private lateinit var binding: ViewCartToolbarBinding

    var trailingClickListener: (() -> Unit)? = null
    var enableTrailing: Boolean = true
        set(value) {
            field = value
            binding.cartToolbarTrailing.visibility = if (field) View.VISIBLE else View.INVISIBLE
        }
    var trailingText: String = ""
        set(value) {
            field = value
            binding.cartToolbarTrailing.text = field
        }
    var cart: List<CartItem> = listOf()
        set(value) {
            field = value
            if (field.isEmpty()) {
                binding.cartToolbarTitle.text = "Cart is Empty"
                return
            }

            val total = field.map { it.amount }.reduce { acc, i -> acc + i }
            binding.cartToolbarTitle.text = "Total:$total"
        }

    init {
        val inflater = LayoutInflater.from(context)
        binding = ViewCartToolbarBinding.inflate(inflater, this, true)
        binding.cartToolbarTrailing.setOnClickListener {
            trailingClickListener?.invoke()
        }
    }
}
