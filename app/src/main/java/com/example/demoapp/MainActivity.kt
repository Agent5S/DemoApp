package com.example.demoapp

import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.demoapp.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.demoapp.viewmodels.MainActivityViewModel

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemReselectedListener {
    @IdRes private final val DEFAULT_NAVIGATION_ITEM_ID = R.id.home_navigation
    private val backStack = arrayListOf(DEFAULT_NAVIGATION_ITEM_ID)
    private val viewModel: MainActivityViewModel by viewModels()
    private var cartFragment: CartFragment? = null
    val navController: NavController get() = findNavController(R.id.mainNavHost)
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setupNavigation()
        if (savedInstanceState == null) {
            navigateToItem(DEFAULT_NAVIGATION_ITEM_ID)
        }

        binding.mainCartSummary.setOnClickListener {
            cartFragment?.let { } ?: run {
                cartFragment = CartFragment()
                cartFragment!!.setOnDismissListener { cartFragment = null }
                cartFragment!!.show(supportFragmentManager, "Cart")
            }
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setupNavigation()
    }

    private fun setupNavigation() {
        binding.mainNavBar.setOnNavigationItemReselectedListener(this)
        binding.mainNavBar.setOnNavigationItemSelectedListener(this)


        viewModel.getCartItems().observe(this, Observer {
            binding.mainCartSummary.cart = it
        })
    }

    override fun onSupportNavigateUp(): Boolean = navController.navigateUp()

    override fun onBackPressed() {
        val a = navController
        when {
            cartFragment?.findNavController()?.navigateUp() ?: false -> { }
            onSupportNavigateUp() -> { }
            backStack.size > 1 -> {
                backStack.removeAt(backStack.size - 1)
                supportFragmentManager.popBackStackImmediate()
                binding.mainNavBar.selectedItemId = backStack.last()
            }
            backStack.last() != DEFAULT_NAVIGATION_ITEM_ID -> {
                backStack.removeAt(backStack.size - 1)
                backStack.add(DEFAULT_NAVIGATION_ITEM_ID)
                navigateToItem(backStack.last())
                binding.mainNavBar.selectedItemId = backStack.last()
            }
            else -> finish()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        navigateToItem(item.itemId)
        return true
    }

    override fun onNavigationItemReselected(item: MenuItem) {
        findNavController(R.id.mainNavHost).popBackStack(item.itemId, true)
    }

    private fun navigateToItem(@IdRes id: Int) {
        val destination = supportFragmentManager.findFragmentByTag(id.toString()) ?:
            NavHostFragment.create(getNavGraphFor(id))

        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.enter_from_right,
                R.anim.exit_to_left,
                R.anim.enter_from_left,
                R.anim.exit_to_right
            )
            .replace(R.id.mainNavHost, destination, id.toString())
            .addToBackStack(null)
            .commit()
            supportFragmentManager.executePendingTransactions()

        if (backStack.last() != id) {
            backStack.add(id)
        }
    }

    private fun getNavGraphFor(@IdRes id: Int) = when(id) {
        R.id.shop_navigation -> R.navigation.shop_navigation
        R.id.orders_navigation -> R.navigation.orders_navigation
        else -> R.navigation.home_navigation
    }

    fun navigateToGraphContaining(uri: Uri) {
        binding.mainNavBar
        for (i in 0 until binding.mainNavBar.menu.size()) {
            val item = binding.mainNavBar.menu.get(i)
            val graphId = getNavGraphFor(item.itemId)
            var fragment: Fragment? = null
            supportFragmentManager.findFragmentByTag(item.itemId.toString())?.let {
                fragment = it
            } ?: run {
                fragment = NavHostFragment.create(graphId)
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.mainNavHost, fragment!!, item.itemId.toString())
                    .commitNow()
            }
            if (fragment!!.findNavController().graph.hasDeepLink(uri)) {
                navigateToItem(item.itemId)
                binding.mainNavBar.selectedItemId = item.itemId
                return
            }
        }
    }
}
