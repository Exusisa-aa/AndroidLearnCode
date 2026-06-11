package com.restaurant.app.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.restaurant.app.R
import com.restaurant.app.data.model.Dish

import android.app.Activity
import android.content.Intent
import androidx.core.app.ActivityOptionsCompat
import com.restaurant.app.ui.detail.DishDetailActivity

class DishAdapter(
    private var items: List<Dish> = emptyList(),
    private val onItemClick: (Dish, ImageView) -> Unit
) : RecyclerView.Adapter<DishAdapter.DishViewHolder>() {

    inner class DishViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imgDish)
        val nameView: TextView = itemView.findViewById(R.id.tvDishName)
        val priceView: TextView = itemView.findViewById(R.id.tvDishPrice)

        fun bind(dish: Dish) {
            nameView.text = dish.name
            priceView.text = "¥ ${dish.price}"
            
            // Transition Name for Shared Element
            imageView.transitionName = "dish_image_${dish.id}"

            val imageUrl = dish.image?.let {
                if (it.startsWith("http")) it else "http://10.0.2.2:8080/common/download?name=$it"
            }

            imageView.load(imageUrl) {
                crossfade(true)
                transformations(RoundedCornersTransformation(16f))
            }

            itemView.setOnClickListener {
                onItemClick(dish, imageView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_dish, parent, false)
        return DishViewHolder(view)
    }

    override fun onBindViewHolder(holder: DishViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    fun submitList(newItems: List<Dish>) {
        items = newItems
        notifyDataSetChanged()
    }
}
