package com.example.demoapp.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.demoapp.databinding.ViewCartSummaryBinding
import com.example.demoapp.models.CartItem

class CartSummary @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private lateinit var binding: ViewCartSummaryBinding

    var cart: List<CartItem> = listOf()
        set(value) {
            field = value
            if (field.isEmpty()) {
                binding.cartSummaryTotal.text = "Cart is Empty"
                return
            }

            val total = field.map { it.amount }.reduce { acc, i -> acc + i }
            binding.cartSummaryTotal.text = "Total:$total"
        }

    init {
        val inflater = LayoutInflater.from(context)
        binding = ViewCartSummaryBinding.inflate(inflater, this, true)
    }
}
