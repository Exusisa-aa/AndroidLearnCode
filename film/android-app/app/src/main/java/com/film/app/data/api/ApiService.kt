package com.film.app.data.api

import com.film.app.data.model.Movie
import com.film.app.data.model.Result
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Body
import retrofit2.http.Query

interface ApiService {
    @GET("movie/list")
    suspend fun getMovies(@Query("status") status: Int = 1): Result<List<Movie>>

    @GET("movie/{id}")
    suspend fun getMovieDetail(@Path("id") id: Long): Result<Movie>

    @POST("order/create")
    suspend fun createOrder(@Body order: Any): Result<Any>

    @POST("user/login")
    suspend fun login(@Body loginRequest: Map<String, String>): Result<com.film.app.data.model.User>

    @POST("user/register")
    suspend fun register(@Body registerRequest: com.film.app.data.model.RegisterRequest): Result<String>

    @GET("schedule/movie/{movieId}")
    suspend fun getSchedules(@Path("movieId") movieId: Long): Result<List<com.film.app.data.model.Schedule>>

    @GET("seat/schedule/{scheduleId}")
    suspend fun getSeats(@Path("scheduleId") scheduleId: Long): Result<List<com.film.app.data.model.Seat>>

    @GET("order/user/{userId}")
    suspend fun getOrders(@Path("userId") userId: Long): Result<List<com.film.app.data.model.Order>>

    @POST("order/pay/{orderId}")
    suspend fun payOrder(@Path("orderId") orderId: Long): Result<Boolean>
}
