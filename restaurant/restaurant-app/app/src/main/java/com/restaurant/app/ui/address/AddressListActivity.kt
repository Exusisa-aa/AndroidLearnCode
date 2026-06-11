package com.restaurant.app.ui.address

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.restaurant.app.data.repo.RestaurantRepository
import com.restaurant.app.databinding.ActivityAddressListBinding
import kotlinx.coroutines.launch

class AddressListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddressListBinding
    private val repository = RestaurantRepository()
    private lateinit var adapter: AddressAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddressListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "我的地址"
        binding.toolbar.setNavigationOnClickListener { finish() }

        adapter = AddressAdapter { address ->
            val intent = Intent(this, AddressEditActivity::class.java).apply {
                putExtra("extra_address", address)
            }
            startActivity(intent)
        }
        binding.rvAddressList.layoutManager = LinearLayoutManager(this)
        binding.rvAddressList.adapter = adapter

        binding.btnAddAddress.setOnClickListener {
            startActivity(Intent(this, AddressEditActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        loadAddresses()
    }

    private fun loadAddresses() {
        lifecycleScope.launch {
            try {
                val list = repository.getAddressList()
                adapter.submitList(list)
            } catch (e: Exception) {
                Toast.makeText(this@AddressListActivity, "加载地址失败", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
