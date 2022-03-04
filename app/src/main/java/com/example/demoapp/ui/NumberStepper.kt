package com.example.demoapp.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.demoapp.databinding.ViewNumberStepperBinding

class NumberStepper @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private lateinit var binding: ViewNumberStepperBinding

    private var onValueChangeListener: ((Int) -> Unit)? = null
    var min = 0
        set(value) {
            field = value
            if (currentValue < field) {
                currentValue = field
            }
        }
    var max = 10
        set(value) {
            field = value
            if (currentValue > field) {
                currentValue = field
            }
        }
    var currentValue = 0
        set(value) {
            val oldValue = field
            field = if (value < 0) 0 else value
            binding.stepperNumberDisplay.text = "x${field}"

            if (oldValue != field) {
                onValueChangeListener?.invoke(field)
            }
        }

    init {
        val inflater = LayoutInflater.from(context)
        binding = ViewNumberStepperBinding.inflate(inflater, this, true)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        currentValue = 0

        binding.stepperButtonsContainer.clipToOutline = true
        binding.stepperButtonsContainer.clipChildren = true

        binding.stepperAdd.setOnClickListener {
            currentValue += 1
        }
        binding.stepperRemove.setOnClickListener {
            currentValue -= 1
        }
    }

    fun setOnValueChangeListener(listener: (Int) -> Unit) {
        this.onValueChangeListener = listener
    }
}
