package com.example.demoapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.ui.setupWithNavController
import com.example.demoapp.databinding.FragmentShopBinding
import com.example.demoapp.listadapters.CategoriesAdapter
import com.example.demoapp.models.Category
import com.example.demoapp.repositories.RepositoryResult
import com.example.demoapp.viewmodels.ShopViewModel

class ShopFragment : NavigationFragment() {
    override val messageTextView: TextView get() = binding.shopMessageText
    override val loadingProgressBar: ProgressBar get() = binding.shopProgressBar
    override val contentView: View get() = binding.shopContent
    override val topLoadingIndicator: View? get() = binding.shopTopLoading.root
    private val viewModel: ShopViewModel by viewModels()
    private val categories get() = viewModel.getCategories()
    private var _binding: FragmentShopBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentShopBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity.setSupportActionBar(binding.shopToolbar)
        binding.shopCollapsingToolbar.setupWithNavController(binding.shopToolbar, mainActivity.navController)
        binding.shopRecycler.isNestedScrollingEnabled = false

        viewModel.getCategories().observe(viewLifecycleOwner, Observer {
            when (it) {
                is RepositoryResult.Error -> {
                    showMessage("Could not load featured items")
                }
                is RepositoryResult.Cached -> {
                    if (it.value.isEmpty()) {
                        showProgressBar()
                    } else {
                        //TODO show separate loader indicators
                        showTopLoadingIndicator()
                        show(it.value)
                    }
                }
                is RepositoryResult.Fresh -> {
                    if (it.value.isEmpty()) {
                        showMessage("There are no featured items to show")
                    } else {
                        hideTopLoadingIndicator()
                        show(it.value)
                    }
                }
            }
        })
    }

    private fun show(model: List<Category>) {
        //Configure recycler view
        binding.shopRecycler.adapter = CategoriesAdapter(model, this)

        //Hide other layouts
        showContent()
    }

    companion object {
        fun newInstance() = ShopFragment()
    }
}
