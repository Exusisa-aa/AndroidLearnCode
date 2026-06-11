package com.restaurant.app.ui.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.restaurant.app.R

data class Review(
    val username: String,
    val avatarUrl: String,
    val content: String,
    val date: String
)

class ReviewAdapter(
    private var items: List<Review> = emptyList()
) : RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivAvatar: ImageView = itemView.findViewById(R.id.iv_avatar)
        val tvUsername: TextView = itemView.findViewById(R.id.tv_username)
        val tvDate: TextView = itemView.findViewById(R.id.tv_date)
        val tvContent: TextView = itemView.findViewById(R.id.tv_content)

        fun bind(item: Review) {
            tvUsername.text = item.username
            tvDate.text = item.date
            tvContent.text = item.content

            ivAvatar.load(item.avatarUrl) {
                crossfade(true)
                transformations(CircleCropTransformation())
                placeholder(R.drawable.ic_placeholder)
                error(R.drawable.ic_placeholder)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_review, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    fun submitList(list: List<Review>) {
        items = list
        notifyDataSetChanged()
    }
}
