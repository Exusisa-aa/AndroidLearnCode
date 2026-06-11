package com.film.app.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.film.app.data.model.Schedule
import com.film.app.databinding.ItemScheduleBinding

class ScheduleAdapter(private val onScheduleClick: (Schedule) -> Unit) :
    RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder>() {

    private var schedules: List<Schedule> = emptyList()

    fun submitList(list: List<Schedule>) {
        schedules = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        val binding = ItemScheduleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ScheduleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        holder.bind(schedules[position])
    }

    override fun getItemCount(): Int = schedules.size

    inner class ScheduleViewHolder(private val binding: ItemScheduleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        
        fun bind(schedule: Schedule) {
            binding.tvTime.text = schedule.startTime.replace("T", " ")
            binding.tvPrice.text = "$${schedule.price}"
            binding.tvHall.text = schedule.hallName ?: "Unknown Hall"
            binding.root.setOnClickListener { onScheduleClick(schedule) }
        }
    }
}
