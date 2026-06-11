package com.restaurant.app.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.restaurant.app.R
import com.restaurant.app.data.model.Category

class CategoryAdapter(
    private var items: List<Category>,
    private val onClick: (Category) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.VH>() {
    private var selectedId: Long? = null
    fun submit(list: List<Category>) {
        items = list
        notifyDataSetChanged()
    }
    fun setSelected(id: Long?) {
        selectedId = id
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return VH(view)
    }
    override fun getItemCount(): Int = items.size
    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        holder.title.text = item.name
        holder.itemView.isSelected = item.id == selectedId
        holder.itemView.setOnClickListener {
            onClick(item)
            setSelected(item.id)
        }
    }
    class VH(v: View) : RecyclerView.ViewHolder(v) {
        val title: TextView = v.findViewById(R.id.tvCategory)
    }
}
