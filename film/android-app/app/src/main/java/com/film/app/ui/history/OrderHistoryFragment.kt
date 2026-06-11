package com.film.app.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.film.app.databinding.FragmentOrderHistoryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderHistoryFragment : Fragment() {

    private var _binding: FragmentOrderHistoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: OrderHistoryViewModel by viewModels()
    private val adapter = OrderAdapter { orderId ->
        viewModel.payOrder(orderId, 
            onSuccess = {
                android.widget.Toast.makeText(context, "支付成功！", android.widget.Toast.LENGTH_SHORT).show()
            },
            onError = { msg ->
                // Keep msg if it's from backend or translate common errors
                val displayMsg = if (msg.contains("failed", true)) "支付失败" else msg
                android.widget.Toast.makeText(context, displayMsg, android.widget.Toast.LENGTH_SHORT).show()
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        binding.rvOrders.layoutManager = LinearLayoutManager(context)
        binding.rvOrders.adapter = adapter
        
        viewModel.loadOrders()
        
        viewModel.orders.observe(viewLifecycleOwner) { orders ->
            adapter.setOrders(orders)
            binding.tvEmpty.isVisible = orders.isEmpty()
            binding.rvOrders.isVisible = orders.isNotEmpty()
        }
        
        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.isVisible = isLoading
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
