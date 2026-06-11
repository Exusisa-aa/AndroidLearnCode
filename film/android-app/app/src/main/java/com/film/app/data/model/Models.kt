package com.film.app.data.model

data class Movie(
    val id: Long,
    val title: String,
    val posterUrl: String,
    val description: String?,
    val videoUrl: String?,
    val releaseDate: String?,
    val duration: Int,
    val director: String?,
    val actors: String?,
    val rating: Double,
    val status: Int
)

data class User(
    val id: Long,
    val username: String,
    val phone: String,
    val avatar: String?,
    val points: Int
)

data class Schedule(
    val id: Long,
    val movieId: Long,
    val hallId: Long,
    val hallName: String?,
    val startTime: String,
    val endTime: String,
    val price: Double
)

data class Seat(
    val id: Long,
    val rowNum: Int,
    val colNum: Int,
    val type: Int, // 1-Standard, 2-Couple
    val status: Int, // 0-Unavailable, 1-Available, 2-Sold
    val label: String
)

data class Order(
    val id: Long,
    val orderNo: String,
    val totalAmount: Double,
    val status: Int, // 0-Pending, 1-Paid
    val createTime: String,
    val movieTitle: String?,
    val hallName: String?,
    val startTime: String?
)

data class LoginResponse(
    val token: String?, // For future use if JWT implemented
    val user: User
)

data class RegisterRequest(
    val username: String,
    val password: String,
    val phone: String
)

data class OrderRequest(
    val userId: Long,
    val scheduleId: Long,
    val seatIds: List<Long>,
    val seatLabels: List<String>
)

data class Result<T>(
    val code: Int,
    val message: String,
    val data: T
)
