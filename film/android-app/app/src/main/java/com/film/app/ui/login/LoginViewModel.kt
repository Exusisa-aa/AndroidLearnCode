package com.film.app.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.film.app.data.model.User
import com.film.app.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _loginResult = MutableLiveData<User?>()
    val loginResult: LiveData<User?> = _loginResult

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun login(username: String, pass: String) {
        viewModelScope.launch {
            _error.value = null
            val result = repository.login(username, pass)
            result.onSuccess { user ->
                _loginResult.value = user
            }.onFailure { exception ->
                _error.value = exception.message ?: "Login failed"
                _loginResult.value = null
            }
        }
    }
}
