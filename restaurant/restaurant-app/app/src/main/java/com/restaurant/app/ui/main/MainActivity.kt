package com.restaurant.app.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.facebook.shimmer.ShimmerFrameLayout
import com.restaurant.app.databinding.ActivityMainBinding
import com.restaurant.app.ui.detail.DishDetailActivity

import com.restaurant.app.ui.address.AddressListActivity

import com.restaurant.app.ui.cart.CartBottomSheetFragment

import androidx.core.view.GravityCompat
import com.restaurant.app.ui.order.OrderHistoryActivity
import com.restaurant.app.ui.login.LoginActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val vm: MainViewModel by viewModels()
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var dishAdapter: DishAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAdapters()
        observeViewModel()
        
        binding.fabMenu.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }

        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                com.restaurant.app.R.id.nav_home -> {
                    // Already here
                }
                com.restaurant.app.R.id.nav_history -> {
                    startActivity(Intent(this, OrderHistoryActivity::class.java))
                }
                com.restaurant.app.R.id.nav_address -> {
                    startActivity(Intent(this, AddressListActivity::class.java))
                }
                com.restaurant.app.R.id.nav_logout -> {
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        binding.fabCart.setOnClickListener {
            val bottomSheet = CartBottomSheetFragment()
            bottomSheet.show(supportFragmentManager, "CartBottomSheet")
        }
        
        vm.loadInitial()
    }

    private fun setupAdapters() {
        categoryAdapter = CategoryAdapter(emptyList()) { vm.onCategorySelected(it.id) }
        binding.rvCategories.layoutManager = LinearLayoutManager(this)
        binding.rvCategories.adapter = categoryAdapter

        dishAdapter = DishAdapter(emptyList()) { dish, imageView ->
            val intent = Intent(this, DishDetailActivity::class.java).apply {
                putExtra("extra_dish", dish)
            }
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                imageView,
                "dish_image_${dish.id}"
            )
            startActivity(intent, options.toBundle())
        }
        binding.rvDishes.layoutManager = GridLayoutManager(this, 2)
        binding.rvDishes.adapter = dishAdapter
    }

    private fun observeViewModel() {
        vm.categories.observe(this) { categoryAdapter.submit(it) }
        vm.dishes.observe(this) { dishAdapter.submitList(it) }
        vm.loading.observe(this) { setLoading(it) }
    }

    private fun setLoading(loading: Boolean) {
        val shimmer = binding.shimmer as ShimmerFrameLayout
        if (loading) {
            shimmer.startShimmer()
            shimmer.showShimmer(true)
            shimmer.visibility = View.VISIBLE
        } else {
            shimmer.stopShimmer()
            shimmer.hideShimmer()
            shimmer.visibility = View.GONE
        }
    }
}
