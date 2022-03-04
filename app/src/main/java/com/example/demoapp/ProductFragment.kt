package com.example.demoapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import com.example.demoapp.databinding.FragmentProductBinding
import com.squareup.picasso.Picasso
import com.example.demoapp.models.CartItem
import com.example.demoapp.models.Product
import com.example.demoapp.models.database
import com.example.demoapp.repositories.RepositoryResult
import com.example.demoapp.viewmodels.ProductViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ProductFragment : NavigationFragment() {
    override val messageTextView: TextView get() = binding.productMessageText
    override val loadingProgressBar: ProgressBar get() = binding.productProgressBar
    override val contentView: View get() = binding.productContent
    override val topLoadingIndicator: View? get() = binding.productTopLoading.root
    private val args: ProductFragmentArgs by navArgs()
    private val viewModel: ProductViewModel by viewModels()
    private val cartItem by lazy { viewModel.getCartItem(args.id) }
    private val product by lazy { viewModel.getProduct(args.id) }
    private var _binding: FragmentProductBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mainActivity.setSupportActionBar(binding.productToolbar)
        binding.productCollapsingToolbar.setupWithNavController(binding.productToolbar, mainActivity.navController)
        binding.productStepper.setOnValueChangeListener(::onStepperChange)
        binding.productAddToCartButton.setOnClickListener(::addToCart)

        cartItem.observe(viewLifecycleOwner, Observer {
            binding.productInCart.text = (it?.amount ?: 0).toString()
        })

        product.observe(viewLifecycleOwner, Observer {
            when (it) {
                is RepositoryResult.Error -> {
                    showMessage("Could not load product")
                }
                is RepositoryResult.Cached -> {
                    it.value?.let {
                        //TODO show separate loader indicators
                        showTopLoadingIndicator()
                        show(it)
                    } ?: run {
                        showProgressBar()
                    }
                }
                is RepositoryResult.Fresh -> {
                    it.value?.let {
                        hideTopLoadingIndicator()
                        show(it)
                    } ?: run {
                        showMessage("Could not load product")
                    }
                }
            }
        })
    }

    private fun show(model: Product) {
        //Configure recycler view
        Picasso
            .get()
            .load(model.imageURL.toUri())
            .placeholder(R.drawable.ic_image)
            .error(R.drawable.ic_broken_image)
            .into(binding.productAppbarImage)
        binding.productCollapsingToolbar.title = model.name

        //Hide other layouts
        showContent()
    }

    private fun onStepperChange(value: Int) {
        binding.productAddToCartButton.isEnabled = value > 0
    }

    private fun addToCart(view: View) {
        val baseAmount = cartItem.value?.amount ?: 0
        val cartItem = CartItem(args.id, baseAmount + binding.productStepper.currentValue)
        binding.productStepper.currentValue = 0
        showTopLoadingIndicator()
        lifecycleScope.launch(Dispatchers.IO) {
            database.cartItemDao().insert(cartItem)
            lifecycleScope.launch {
                hideTopLoadingIndicator()
            }
        }
    }

    companion object {
        fun newInstance() = ProductFragment()
    }
}
