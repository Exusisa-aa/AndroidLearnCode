package com.film.app.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.film.app.data.model.Order
import com.film.app.data.repository.MovieRepository
import com.film.app.utils.UserSession
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderHistoryViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _orders = MutableLiveData<List<Order>>()
    val orders: LiveData<List<Order>> = _orders

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun loadOrders() {
        val user = UserSession.user
        if (user == null) {
            _orders.value = emptyList()
            return
        }

        viewModelScope.launch {
            _loading.value = true
            val orderList = repository.getOrders(user.id)
            _orders.value = orderList
            _loading.value = false
        }
    }

    fun payOrder(orderId: Long, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            _loading.value = true
            val success = repository.payOrder(orderId)
            _loading.value = false
            if (success) {
                onSuccess()
                loadOrders() // Refresh list
            } else {
                onError("Payment failed")
            }
        }
    }
}
