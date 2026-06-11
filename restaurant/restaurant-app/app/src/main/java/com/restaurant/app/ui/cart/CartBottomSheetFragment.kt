package com.restaurant.app.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.restaurant.app.R
import com.restaurant.app.data.model.Orders
import com.restaurant.app.data.model.ShoppingCart
import com.restaurant.app.data.repo.RestaurantRepository
import kotlinx.coroutines.launch
import java.math.BigDecimal

class CartBottomSheetFragment : BottomSheetDialogFragment() {

    private val repository = RestaurantRepository()
    private lateinit var adapter: CartAdapter
    private lateinit var tvTotal: TextView
    private lateinit var btnCheckout: Button
    private lateinit var tvClear: TextView
    private var cartItems: List<ShoppingCart> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cart_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvCart = view.findViewById<RecyclerView>(R.id.rv_cart)
        tvTotal = view.findViewById(R.id.tv_total_price)
        btnCheckout = view.findViewById(R.id.btn_checkout)
        tvClear = view.findViewById(R.id.tv_clear)

        adapter = CartAdapter()
        rvCart.layoutManager = LinearLayoutManager(context)
        rvCart.adapter = adapter

        tvClear.setOnClickListener {
            lifecycleScope.launch {
                if (repository.cleanCart()) {
                    loadCart()
                }
            }
        }

        btnCheckout.setOnClickListener {
            if (cartItems.isEmpty()) {
                Toast.makeText(context, "购物车为空", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            submitOrder()
        }

        loadCart()
    }

    private fun loadCart() {
        lifecycleScope.launch {
            try {
                cartItems = repository.getCartList()
                // Debug log
                android.util.Log.d("Cart", "Loaded items: ${cartItems.size}")
                adapter.submitList(cartItems)
                calculateTotal()
            } catch (e: Exception) {
                Toast.makeText(context, "错误: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun calculateTotal() {
        var total = 0.0
        cartItems.forEach {
            total += (it.amount ?: 0.0) * it.number
        }
        tvTotal.text = "¥ %.2f".format(total)
    }

    private fun submitOrder() {
        lifecycleScope.launch {
            try {
                // 1. Get Default Address
                val address = repository.getDefaultAddress()
                if (address == null) {
                    Toast.makeText(context, "请先设置默认地址", Toast.LENGTH_LONG).show()
                    return@launch
                }

                // 2. Submit Order
                val order = Orders(
                    addressBookId = address.id,
                    payMethod = 1, // WeChat
                    remark = "Android App Order"
                )

                val success = repository.submitOrder(order)
                if (success) {
                    Toast.makeText(context, "订单提交成功!", Toast.LENGTH_LONG).show()
                    dismiss()
                    // Refresh parent if needed
                } else {
                    Toast.makeText(context, "订单提交失败", Toast.LENGTH_SHORT).show()
                }

            } catch (e: Exception) {
                Toast.makeText(context, "错误: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
