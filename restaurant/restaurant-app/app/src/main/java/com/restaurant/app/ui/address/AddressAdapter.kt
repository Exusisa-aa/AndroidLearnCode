package com.restaurant.app.ui.address

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.restaurant.app.R
import com.restaurant.app.data.model.AddressBook

class AddressAdapter(
    private var items: List<AddressBook> = emptyList(),
    private val onEditClick: (AddressBook) -> Unit
) : RecyclerView.Adapter<AddressAdapter.AddressViewHolder>() {

    inner class AddressViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tv_name)
        val tvSex: TextView = itemView.findViewById(R.id.tv_sex)
        val tvPhone: TextView = itemView.findViewById(R.id.tv_phone)
        val tvAddress: TextView = itemView.findViewById(R.id.tv_address)
        val tvTag: TextView = itemView.findViewById(R.id.tv_tag)
        val ivEdit: ImageView = itemView.findViewById(R.id.iv_edit)

        fun bind(item: AddressBook) {
            tvName.text = item.consignee
            tvSex.text = if (item.sex == "1") "Mr." else "Ms."
            tvPhone.text = item.phone
            tvAddress.text = "${item.provinceName ?: ""} ${item.cityName ?: ""} ${item.districtName ?: ""} ${item.detail ?: ""}"
            
            if (item.isDefault == 1) {
                tvTag.visibility = View.VISIBLE
                tvTag.text = "Default"
            } else {
                tvTag.visibility = View.GONE
            }

            ivEdit.setOnClickListener { onEditClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_address, parent, false)
        return AddressViewHolder(view)
    }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    fun submitList(list: List<AddressBook>) {
        items = list
        notifyDataSetChanged()
    }
}
