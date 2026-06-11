package com.restaurant.app.data.api

import com.restaurant.app.data.model.AddressBook
import com.restaurant.app.data.model.Category
import com.restaurant.app.data.model.Dish
import com.restaurant.app.data.model.Result
import com.restaurant.app.data.model.User
import com.restaurant.app.data.model.ShoppingCart
import com.restaurant.app.data.model.Orders
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

import com.restaurant.app.data.model.Page

interface ApiService {
    @GET("category/list")
    suspend fun getCategories(@Query("type") type: Int = 1): Result<List<Category>>

    @GET("dish/list")
    suspend fun getDishes(@Query("categoryId") categoryId: Long): Result<List<Dish>>

    // User
    @POST("user/sendMsg")
    suspend fun sendMsg(@Body user: User): Result<String>

    @POST("user/login")
    suspend fun login(@Body map: Map<String, String>): Result<User>

    // Address
    @GET("addressBook/list")
    suspend fun getAddressList(): Result<List<AddressBook>>

    @POST("addressBook")
    suspend fun addAddress(@Body addressBook: AddressBook): Result<AddressBook>

    @PUT("addressBook")
    suspend fun updateAddress(@Body addressBook: AddressBook): Result<String>

    @PUT("addressBook/default")
    suspend fun setDefaultAddress(@Body addressBook: AddressBook): Result<AddressBook>

    @GET("addressBook/default")
    suspend fun getDefaultAddress(): Result<AddressBook>

    // Shopping Cart
    @GET("shoppingCart/list")
    suspend fun getCartList(): Result<List<ShoppingCart>>

    @POST("shoppingCart/add")
    suspend fun addToCart(@Body cart: ShoppingCart): Result<ShoppingCart>

    @retrofit2.http.DELETE("shoppingCart/clean")
    suspend fun cleanCart(): Result<String>

    // Order
    @POST("order/submit")
    suspend fun submitOrder(@Body order: Orders): Result<String>

    @GET("order/userPage")
    suspend fun getOrderHistory(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): Result<Page<Orders>>
}
