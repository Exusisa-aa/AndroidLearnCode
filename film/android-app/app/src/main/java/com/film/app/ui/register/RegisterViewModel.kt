package com.film.app.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.film.app.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _registerResult = MutableLiveData<Boolean>()
    val registerResult: LiveData<Boolean> = _registerResult

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun register(username: String, pass: String, phone: String) {
        viewModelScope.launch {
            _error.value = null
            val result = repository.register(username, pass, phone)
            result.onSuccess {
                _registerResult.value = true
            }.onFailure { exception ->
                _error.value = exception.message ?: "Registration failed"
                _registerResult.value = false
            }
        }
    }
}
