package com.restaurant.app.ui.detail

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.restaurant.app.data.model.Dish
import com.restaurant.app.databinding.ActivityDishDetailBinding
import coil.load
import coil.transform.RoundedCornersTransformation

import androidx.lifecycle.lifecycleScope
import com.restaurant.app.data.model.ShoppingCart
import com.restaurant.app.data.repo.RestaurantRepository
import kotlinx.coroutines.launch

import androidx.recyclerview.widget.LinearLayoutManager

class DishDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDishDetailBinding
    private var dish: Dish? = null
    private val repository = RestaurantRepository()
    private lateinit var reviewAdapter: ReviewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        binding = ActivityDishDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get Dish from intent
        dish = intent.getParcelableExtra("extra_dish")

        if (dish == null) {
            finish()
            return
        }

        setupUI()
    }

    private fun setupUI() {
        dish?.let { d ->
            setSupportActionBar(binding.toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            binding.toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }
            supportActionBar?.title = d.name

            // Load Image with transition name
            binding.ivDishImage.transitionName = "dish_image_${d.id}"
            val imageUrl = if (d.image?.startsWith("http") == true) {
                d.image
            } else {
                "http://10.0.2.2:8080/common/download?name=${d.image}" // Simple concatenation for demo
            }
            
            binding.ivDishImage.load(imageUrl) {
                crossfade(true)
                // Use a placeholder or error if needed, but for shared element, we might want to be careful
                // For now standard load is fine
            }

            binding.tvDishName.text = d.name
            binding.tvDishPrice.text = "¥ ${d.price}"
            binding.tvDishDesc.text = d.description ?: "No description available."

            binding.fabAddCart.setOnClickListener {
                addToCart(d)
            }
            
            setupReviews()
        }
    }

    private fun setupReviews() {
        reviewAdapter = ReviewAdapter()
        binding.rvReviews.layoutManager = LinearLayoutManager(this)
        binding.rvReviews.adapter = reviewAdapter
        
        // Use dish id/name to generate context-aware reviews
        val dishName = dish?.name ?: "这道菜"
        val dishId = dish?.id ?: 0
        val baseSeed = dishId.toInt()
        
        val userNames = listOf("Alice", "Bob", "Charlie", "David", "Eve", "Frank", "Grace", "Helen")
        
        // Templates for reviews
        val goodComments = listOf(
            "味道非常好，很正宗！",
            "${dishName}分量很足，性价比高。",
            "食材很新鲜，下次还会点${dishName}。",
            "太好吃了，一定要尝尝！",
            "是我吃过最好吃的${dishName}。"
        )
        
        val averageComments = listOf(
            "稍微有点辣，但是很过瘾。",
            "送餐速度很快，但${dishName}有点凉了。",
            "包装很仔细，没有洒漏。",
            "还可以，无功无过。",
            "比我想象中要油一点。"
        )
        
        val poorComments = listOf(
            "一般般，不太合我口味。",
            "感觉不太新鲜。",
            "分量有点少。",
            "和图片不太符。"
        )

        val mockReviews = mutableListOf<Review>()
        // Generate 3-8 reviews randomly based on dish id
        val reviewCount = 3 + (baseSeed % 6)
        
        for (i in 0 until reviewCount) {
            val userIndex = (baseSeed + i * 3) % userNames.size
            val name = userNames[userIndex]
            
            // Determine sentiment based on some pseudo-random logic
            val sentiment = (baseSeed + i) % 10
            val content = when {
                sentiment < 6 -> goodComments[(baseSeed + i) % goodComments.size] // 60% Good
                sentiment < 9 -> averageComments[(baseSeed + i) % averageComments.size] // 30% Average
                else -> poorComments[(baseSeed + i) % poorComments.size] // 10% Poor
            }

            mockReviews.add(Review(
                username = name,
                // Use robohash as it's more reliable for Android and supports identicons
                // ui-avatars sometimes has SSL/network issues on emulators
                avatarUrl = "https://robohash.org/${name}?set=set4", 
                content = content,
                date = "2026-03-${10 - (i % 5)}"
            ))
        }
        
        reviewAdapter.submitList(mockReviews)
    }

    private fun addToCart(dish: Dish) {
        val cartItem = ShoppingCart(
            dishId = dish.id,
            name = dish.name,
            image = dish.image,
            amount = dish.price,
            dishFlavor = null
        )

        lifecycleScope.launch {
            try {
                val success = repository.addToCart(cartItem)
                if (success) {
                    Toast.makeText(this@DishDetailActivity, "已加入购物车", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@DishDetailActivity, "添加失败", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@DishDetailActivity, "错误: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
