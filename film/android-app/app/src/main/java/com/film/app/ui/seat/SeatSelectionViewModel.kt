package com.film.app.ui.seat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.film.app.data.model.Seat
import com.film.app.data.model.OrderRequest
import com.film.app.data.repository.MovieRepository
import com.film.app.utils.UserSession
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeatSelectionViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _seats = MutableLiveData<List<Seat>>()
    val seats: LiveData<List<Seat>> = _seats

    private val _orderResult = MutableLiveData<Boolean>()
    val orderResult: LiveData<Boolean> = _orderResult

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun loadSeats(scheduleId: Long) {
        viewModelScope.launch {
            try {
                val seatList = repository.getSeats(scheduleId)
                // Map API Seat to SeatView.SeatData if needed, but SeatView uses SeatData inner class.
                // Actually SeatView.SeatData matches Seat model closely.
                // But SeatView.SeatData is inside SeatView class.
                // We should probably map it in Fragment or change SeatView to use Seat model.
                // For now, let's just expose the list.
                _seats.value = seatList
            } catch (e: Exception) {
                _error.value = "Failed to load seats: ${e.message}"
            }
        }
    }

    fun createOrder(scheduleId: Long, selectedSeatIds: List<Long>, selectedSeatLabels: List<String>) {
        val user = UserSession.user
        if (user == null) {
            _error.value = "Please login first"
            return
        }

        viewModelScope.launch {
            val request = OrderRequest(
                userId = user.id,
                scheduleId = scheduleId,
                seatIds = selectedSeatIds,
                seatLabels = selectedSeatLabels
            )
            val result = repository.createOrder(request)
            result.onSuccess {
                _orderResult.value = true
            }.onFailure { e ->
                _error.value = e.message ?: "Order failed"
                _orderResult.value = false
            }
        }
    }
}
