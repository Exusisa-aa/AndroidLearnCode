package com.restaurant.app.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Dish(
    val id: Long,
    val name: String,
    val categoryId: Long,
    val price: Double?,
    val code: String?,
    val image: String?,
    val description: String?,
    val status: Int?,
    val sort: Int?,
    val flavors: List<DishFlavor>?
) : Parcelable
