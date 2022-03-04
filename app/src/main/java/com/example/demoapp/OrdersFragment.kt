package com.example.demoapp

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.ui.setupWithNavController
import com.example.demoapp.databinding.FragmentOrdersBinding
import com.example.demoapp.viewmodels.OrdersViewModel


class OrdersFragment : NavigationFragment() {
    override val messageTextView: TextView get() = TODO("Not yet implemented")
    override val loadingProgressBar: ProgressBar get() = TODO("Not yet implemented")
    override val contentView: View get() = TODO("Not yet implemented")
    private lateinit var viewModel: OrdersViewModel
    private var _binding: FragmentOrdersBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOrdersBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mainActivity.setSupportActionBar(binding.ordersToolbar)
        binding.ordersCollapsingToolbar.setupWithNavController(binding.ordersToolbar, mainActivity.navController)
        viewModel = ViewModelProvider(this).get(OrdersViewModel::class.java)
        // TODO: Use the ViewModel
    }

    companion object {
        fun newInstance() = OrdersFragment()
    }
}
