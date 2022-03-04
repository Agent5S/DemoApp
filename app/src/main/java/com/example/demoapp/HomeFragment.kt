package com.example.demoapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.ui.setupWithNavController
import com.example.demoapp.databinding.FragmentHomeBinding
import com.squareup.picasso.Picasso
import com.example.demoapp.listadapters.PreviewListAdapter
import com.example.demoapp.models.FeaturedList
import com.example.demoapp.repositories.RepositoryResult
import com.example.demoapp.viewmodels.HomeViewModel

class HomeFragment() : NavigationFragment() {
    override val messageTextView: TextView get() = binding.homeMessageText
    override val loadingProgressBar: ProgressBar get() = binding.homeProgressBar
    override val contentView: View get() = binding.homeContent
    override val topLoadingIndicator: View? get() = binding.homeTopLoading.root
    private val viewModel: HomeViewModel by viewModels()
    private val list get() = viewModel.getList()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity.setSupportActionBar(binding.homeToolbar)
        binding.homeCollapsingToolbar.setupWithNavController(binding.homeToolbar, mainActivity.navController)

        list.observe(viewLifecycleOwner, Observer {
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

    private fun show(model: List<FeaturedList>) {
        // Configure billboard
        binding.homeFeaturedBillboard.root.findViewById<TextView>(R.id.featuredBillboardText).text = model[0].items[0].name
        Picasso
            .get()
            .load(model[0].items[0].imageURL)
            .placeholder(R.drawable.ic_image)
            .error(R.drawable.ic_broken_image)
            .into(binding.homeFeaturedBillboard.root.findViewById<ImageView>(R.id.featuredBillboardImage))

        //Configure recycler view
        binding.homeFeaturedLists.adapter = PreviewListAdapter(model, this)

        showContent()
    }

    companion object {
        fun newInstance() = HomeFragment()
    }
}
