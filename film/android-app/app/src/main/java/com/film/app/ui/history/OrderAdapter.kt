package com.film.app.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.film.app.data.model.Order
import com.film.app.databinding.ItemOrderBinding

import androidx.core.content.ContextCompat
import com.film.app.R

class OrderAdapter(private val onPayClick: (Long) -> Unit) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    private var orders: List<Order> = emptyList()

    fun setOrders(newOrders: List<Order>) {
        this.orders = newOrders
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(binding, onPayClick)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(orders[position])
    }

    override fun getItemCount(): Int = orders.size

    class OrderViewHolder(private val binding: ItemOrderBinding, private val onPayClick: (Long) -> Unit) : RecyclerView.ViewHolder(binding.root) {
        fun bind(order: Order) {
            binding.tvOrderNo.text = "${order.orderNo}"
            binding.tvAmount.text = "$${order.totalAmount}"
            binding.tvMovieTitle.text = order.movieTitle ?: "未知电影"
            
            val startTime = order.startTime?.replace("T", " ") ?: ""
            val hallName = order.hallName ?: ""
            binding.tvHallInfo.text = "$hallName • $startTime"
            
            val statusText = when (order.status) {
                0 -> "待支付"
                1 -> "已支付"
                2 -> "已取消"
                3 -> "已退款"
                else -> "未知状态"
            }
            binding.tvStatus.text = statusText
            
            val statusColor = when (order.status) {
                0 -> R.color.colorGold
                1 -> R.color.holo_green_light
                2 -> R.color.holo_red_light
                else -> R.color.text_tertiary
            }
            // Note: We'd need to dynamically change the drawable tint, but for now let's just leave the default pill color or set text color if background is static.
            // Let's assume bg_status_pill is just a shape and we can tint it.
            binding.tvStatus.background.setTint(ContextCompat.getColor(binding.root.context, statusColor))
            binding.tvStatus.setTextColor(ContextCompat.getColor(binding.root.context, if(order.status==0) R.color.black else R.color.white))

            binding.tvDate.text = "创建时间: " + order.createTime.replace("T", " ")
            
            if (order.status == 0) { // Pending
                binding.btnPay.visibility = android.view.View.VISIBLE
                binding.btnPay.setOnClickListener {
                    onPayClick(order.id)
                }
            } else {
                binding.btnPay.visibility = android.view.View.GONE
            }
        }
    }
}
