package com.restaurant.app.data.repo

import com.restaurant.app.data.api.NetworkModule
import com.restaurant.app.data.model.AddressBook
import com.restaurant.app.data.model.Category
import com.restaurant.app.data.model.Dish
import com.restaurant.app.data.model.Orders
import com.restaurant.app.data.model.ShoppingCart
import com.restaurant.app.data.model.User

class RestaurantRepository {
    suspend fun loadCategories(): List<Category> {
        val res = NetworkModule.api.getCategories()
        return res.data ?: emptyList()
    }

    suspend fun loadDishes(categoryId: Long): List<Dish> {
        val res = NetworkModule.api.getDishes(categoryId)
        return res.data ?: emptyList()
    }

    suspend fun sendMsg(phone: String): Boolean {
        val res = NetworkModule.api.sendMsg(User(phone = phone))
        return res.code == 1
    }

    suspend fun login(phone: String, code: String): User? {
        val map = mapOf("phone" to phone, "code" to code)
        val res = NetworkModule.api.login(map)
        return if (res.code == 1) res.data else null
    }

    suspend fun getAddressList(): List<AddressBook> {
        val res = NetworkModule.api.getAddressList()
        return res.data ?: emptyList()
    }

    suspend fun addAddress(address: AddressBook): Boolean {
        val res = NetworkModule.api.addAddress(address)
        return res.code == 1
    }

    suspend fun setDefaultAddress(address: AddressBook): Boolean {
        val res = NetworkModule.api.setDefaultAddress(address)
        return res.code == 1
    }
    
    suspend fun getDefaultAddress(): AddressBook? {
        val res = NetworkModule.api.getDefaultAddress()
        return res.data
    }

    suspend fun getCartList(): List<ShoppingCart> {
        val res = NetworkModule.api.getCartList()
        return res.data ?: emptyList()
    }

    suspend fun addToCart(cart: ShoppingCart): Boolean {
        val res = NetworkModule.api.addToCart(cart)
        return res.code == 1
    }

    suspend fun cleanCart(): Boolean {
        val res = NetworkModule.api.cleanCart()
        return res.code == 1
    }
    
    suspend fun submitOrder(order: Orders): Boolean {
        val res = NetworkModule.api.submitOrder(order)
        return res.code == 1
    }

    suspend fun getOrderHistory(page: Int = 1, pageSize: Int = 10): List<Orders> {
        val res = NetworkModule.api.getOrderHistory(page, pageSize)
        return res.data?.records ?: emptyList()
    }
}
