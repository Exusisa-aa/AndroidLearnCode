package com.film.app.ui.seat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.film.app.databinding.FragmentSeatSelectionBinding
import dagger.hilt.android.AndroidEntryPoint

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.film.app.widget.SeatView

@AndroidEntryPoint
class SeatSelectionFragment : Fragment() {

    private var _binding: FragmentSeatSelectionBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SeatSelectionViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSeatSelectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        val scheduleId = arguments?.getLong("scheduleId")
        if (scheduleId == null) {
            Toast.makeText(context, "无效的排片ID", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
            return
        }
        
        viewModel.loadSeats(scheduleId)
        
        viewModel.seats.observe(viewLifecycleOwner) { seats ->
            val seatDataList = seats.map { seat ->
                SeatView.SeatData(
                    id = seat.id,
                    row = seat.rowNum,
                    col = seat.colNum,
                    status = seat.status,
                    label = seat.label
                )
            }
            binding.seatView.setData(seatDataList)
            binding.tvSelectedSeats.text = "未选择座位"
        }
        
        viewModel.orderResult.observe(viewLifecycleOwner) { success ->
            if (success) {
                Toast.makeText(context, "订单创建成功！请前往支付。", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
        }
        
        viewModel.error.observe(viewLifecycleOwner) { error ->
            if (!error.isNullOrEmpty()) {
                Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
            }
        }
        
        binding.seatView.onSeatSelectedListener = { labels ->
            if (labels.isEmpty()) {
                binding.tvSelectedSeats.text = "未选择座位"
            } else {
                binding.tvSelectedSeats.text = "已选: " + labels.joinToString(", ")
            }
        }
        
        binding.btnConfirm.setOnClickListener {
            val selectedIds = binding.seatView.getSelectedSeatIds()
            val selectedLabels = binding.seatView.getSelectedSeatLabels()
            if (selectedIds.isNotEmpty()) {
                viewModel.createOrder(scheduleId, selectedIds, selectedLabels)
            } else {
                Toast.makeText(context, "请至少选择一个座位", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
