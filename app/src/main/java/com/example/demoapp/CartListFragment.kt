package com.example.demoapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.demoapp.databinding.FragmentCartListBinding
import com.example.demoapp.listadapters.CartListAdapter
import com.example.demoapp.viewmodels.CartListViewModel


class CartListFragment : NavigationFragment() {
    override val messageTextView: TextView get() = binding.cartMessageText
    override val loadingProgressBar: ProgressBar get() = binding.cartProgressBar
    override val contentView: View get() = binding.cartContent
    private val viewModel: CartListViewModel by viewModels()
    private var _binding: FragmentCartListBinding? = null;
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =  FragmentCartListBinding.inflate(inflater, container, false)
        val view = binding.root;
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.cartToolbar.trailingText = "CHECKOUT"
        binding.cartToolbar.trailingClickListener = ::navigateToCheckout

        showProgressBar()
        viewModel.getCartItems().observe(viewLifecycleOwner, Observer {
            binding.cartToolbar.cart = it
            binding.cartToolbar.enableTrailing = it.isNotEmpty()

            binding.cartContent.adapter = CartListAdapter(it, this)
            showContent()
        })
    }

    fun navigateToCheckout() {
        findNavController().navigate(R.id.checkoutFragment)
    }

    companion object {
        fun newInstance() = CartListFragment()
    }
}
