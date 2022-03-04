package com.example.demoapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import com.example.demoapp.databinding.FragmentCategoryBinding
import com.example.demoapp.listadapters.ProductListAdapter
import com.example.demoapp.models.Product
import com.example.demoapp.repositories.RepositoryResult
import com.example.demoapp.viewmodels.CategoryViewModel

class CategoryFragment : NavigationFragment() {
    override val messageTextView: TextView get() = binding.categoryMessageText
    override val loadingProgressBar: ProgressBar get() = binding.categoryProgressBar
    override val contentView: View get() = binding.categoryContent
    override val topLoadingIndicator: View? get() = binding.categoryTopLoading.root
    private val args: CategoryFragmentArgs by navArgs()
    private val viewModel: CategoryViewModel by viewModels()
    var _binding: FragmentCategoryBinding? = null;
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mainActivity.setSupportActionBar(binding.categoryToolbar)
        binding.categoryToolbar.setupWithNavController(mainActivity.navController)

        viewModel.getCategory(args.categoryId).observe(viewLifecycleOwner, Observer {
            when (it) {
                is RepositoryResult.Cached -> binding.categoryToolbar.title = it.value?.name ?: ""
                is RepositoryResult.Fresh -> binding.categoryToolbar.title = it.value?.name ?: ""
            }
        })

        viewModel.getProducts(args.categoryId).observe(viewLifecycleOwner, Observer {
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

    private fun show(model: List<Product>) {
        //Configure recycler view
        binding.categoryContent.adapter = ProductListAdapter(model, this)

        //Hide other layouts
        showContent()
    }

    companion object {
        fun newInstance() = CategoryFragment()
    }
}
