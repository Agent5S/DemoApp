package com.example.demoapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.demoapp.databinding.FragmentCheckoutBinding
import com.example.demoapp.databinding.LayoutCartToolbarBinding
import com.example.demoapp.viewmodels.CheckoutViewModel


class CheckoutFragment : Fragment() {
    private val viewModel: CheckoutViewModel by viewModels()
    private var _binding: FragmentCheckoutBinding? = null
    private val binding get() = _binding!!
    private var _toolbarBinding: LayoutCartToolbarBinding? = null
    private val toolbarBinding get() = _toolbarBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCheckoutBinding.inflate(inflater, container, false)
        _toolbarBinding = LayoutCartToolbarBinding.bind(binding.root)
        val view = binding.root
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        toolbarBinding.cartTitle.text = "Cart is empty"
        toolbarBinding.cartTrailingButton.visibility = View.GONE
    }

    companion object {
        fun newInstance() = CheckoutFragment()
    }
}
