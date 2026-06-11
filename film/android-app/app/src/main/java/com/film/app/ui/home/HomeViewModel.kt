package com.film.app.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.film.app.data.model.Movie
import com.film.app.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _hotMovies = MutableLiveData<List<Movie>>()
    val hotMovies: LiveData<List<Movie>> = _hotMovies

    private val _comingMovies = MutableLiveData<List<Movie>>()
    val comingMovies: LiveData<List<Movie>> = _comingMovies

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        fetchMovies()
    }

    private fun fetchMovies() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // Fetch Hot Movies (status = 1)
                _hotMovies.value = repository.getMovies(1)
                
                // Fetch Coming Soon Movies (status = 2)
                _comingMovies.value = repository.getMovies(2)
            } catch (e: Exception) {
                e.printStackTrace()
                // Handle error
            } finally {
                _isLoading.value = false
            }
        }
    }
}
