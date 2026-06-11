package com.restaurant.app.ui.cart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.restaurant.app.R
import com.restaurant.app.data.model.ShoppingCart
import coil.load
import coil.transform.RoundedCornersTransformation

class CartAdapter(
    private var items: List<ShoppingCart> = emptyList()
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivDish: ImageView = itemView.findViewById(R.id.iv_dish)
        val tvName: TextView = itemView.findViewById(R.id.tv_name)
        val tvFlavor: TextView = itemView.findViewById(R.id.tv_flavor)
        val tvPrice: TextView = itemView.findViewById(R.id.tv_price)
        val tvCount: TextView = itemView.findViewById(R.id.tv_count)

        fun bind(item: ShoppingCart) {
            tvName.text = item.name
            tvFlavor.text = item.dishFlavor ?: ""
            tvPrice.text = "¥ ${item.amount}"
            tvCount.text = "x ${item.number}"

            val imageUrl = if (item.image?.startsWith("http") == true) {
                item.image
            } else {
                "http://10.0.2.2:8080/common/download?name=${item.image}"
            }
            ivDish.load(imageUrl) {
                transformations(RoundedCornersTransformation(8f))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    fun submitList(list: List<ShoppingCart>) {
        items = list
        notifyDataSetChanged()
    }
}
