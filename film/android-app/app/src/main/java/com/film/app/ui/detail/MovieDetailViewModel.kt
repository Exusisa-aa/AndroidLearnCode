package com.film.app.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.film.app.data.model.Movie
import com.film.app.data.model.Schedule
import com.film.app.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _movie = MutableLiveData<Movie?>()
    val movie: LiveData<Movie?> = _movie

    private val _schedules = MutableLiveData<List<Schedule>>()
    val schedules: LiveData<List<Schedule>> = _schedules

    fun loadMovie(movieId: Long) {
        viewModelScope.launch {
            val movieDetail = repository.getMovieDetail(movieId)
            _movie.value = movieDetail
            
            if (movieDetail != null) {
                val scheduleList = repository.getSchedules(movieId)
                _schedules.value = scheduleList
            }
        }
    }
}
