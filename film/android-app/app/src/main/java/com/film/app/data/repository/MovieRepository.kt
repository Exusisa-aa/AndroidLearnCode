package com.film.app.data.repository

import com.film.app.data.api.ApiService
import com.film.app.data.model.Movie
import com.film.app.data.model.Order
import com.film.app.data.model.Schedule
import com.film.app.data.model.Seat
import com.film.app.data.model.User
import javax.inject.Inject
import javax.inject.Singleton
import com.film.app.data.model.RegisterRequest
import com.film.app.data.model.Result as ApiResult

@Singleton
class MovieRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getMovies(status: Int): List<Movie> {
        val result = apiService.getMovies(status)
        return if (result.code == 200) {
            result.data
        } else {
            emptyList()
        }
    }

    suspend fun getMovieDetail(id: Long): Movie? {
        val result = apiService.getMovieDetail(id)
        return if (result.code == 200) result.data else null
    }

    suspend fun login(username: String, password: String): Result<User> {
        return try {
            val result = apiService.login(mapOf("username" to username, "password" to password))
            if (result.code == 200) {
                Result.success(result.data)
            } else {
                Result.failure(Exception(result.message))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun register(username: String, password: String, phone: String): Result<String> {
        return try {
            val result = apiService.register(RegisterRequest(username, password, phone))
            if (result.code == 200) {
                Result.success(result.data)
            } else {
                Result.failure(Exception(result.message))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getSchedules(movieId: Long): List<Schedule> {
        return try {
            val result = apiService.getSchedules(movieId)
            if (result.code == 200) result.data else emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getSeats(scheduleId: Long): List<Seat> {
        return try {
            val result = apiService.getSeats(scheduleId)
            if (result.code == 200) result.data else emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun createOrder(orderRequest: Any): Result<Any> {
        return try {
            val result = apiService.createOrder(orderRequest)
            if (result.code == 200) {
                Result.success(result.data)
            } else {
                Result.failure(Exception(result.message))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getOrders(userId: Long): List<Order> {
        return try {
            val result = apiService.getOrders(userId)
            if (result.code == 200) result.data else emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun payOrder(orderId: Long): Boolean {
        return try {
            val result = apiService.payOrder(orderId)
            result.code == 200 && result.data
        } catch (e: Exception) {
            false
        }
    }
}
