package com.restaurant.app.ui.order

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.restaurant.app.R
import com.restaurant.app.data.model.Orders

class OrderHistoryAdapter(
    private var items: List<Orders> = emptyList()
) : RecyclerView.Adapter<OrderHistoryAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTime: TextView = itemView.findViewById(R.id.tv_order_time)
        val tvStatus: TextView = itemView.findViewById(R.id.tv_order_status)
        val tvSummary: TextView = itemView.findViewById(R.id.tv_order_summary)
        val tvAmount: TextView = itemView.findViewById(R.id.tv_order_amount)

        fun bind(item: Orders) {
            tvTime.text = item.orderTime?.replace("T", " ")
            
            // Status mapping (Simplified)
            tvStatus.text = when(item.status) {
                1 -> "待付款"
                2 -> "待配送"
                3 -> "已送达"
                4 -> "已完成"
                5 -> "已取消"
                else -> "未知状态"
            }
            
            tvAmount.text = "¥ ${item.amount}"

            // Summary
            val summary = item.orderDetails?.joinToString(", ") { detail ->
                "${detail.name} x${detail.number}"
            } ?: "暂无详情"
            tvSummary.text = summary
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_order_history, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    fun submitList(list: List<Orders>) {
        items = list
        notifyDataSetChanged()
    }
}
