package com.restaurant.app.ui.order

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.restaurant.app.data.repo.RestaurantRepository
import com.restaurant.app.databinding.ActivityOrderHistoryBinding
import kotlinx.coroutines.launch

class OrderHistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrderHistoryBinding
    private val repository = RestaurantRepository()
    private lateinit var adapter: OrderHistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "历史订单"
        binding.toolbar.setNavigationOnClickListener { finish() }

        adapter = OrderHistoryAdapter()
        binding.rvOrderHistory.layoutManager = LinearLayoutManager(this)
        binding.rvOrderHistory.adapter = adapter

        loadOrderHistory()
    }

    private fun loadOrderHistory() {
        lifecycleScope.launch {
            try {
                // Load page 1, size 100 for simplicity
                val list = repository.getOrderHistory(1, 100)
                if (list.isEmpty()) {
                    Toast.makeText(this@OrderHistoryActivity, "暂无历史订单", Toast.LENGTH_SHORT).show()
                }
                adapter.submitList(list)
            } catch (e: Exception) {
                Toast.makeText(this@OrderHistoryActivity, "加载失败: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
