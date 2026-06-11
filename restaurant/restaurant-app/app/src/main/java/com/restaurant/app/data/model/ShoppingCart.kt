package com.restaurant.app.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ShoppingCart(
    val id: Long? = null,
    val name: String? = null,
    val image: String? = null,
    val userId: Long? = null,
    val dishId: Long? = null,
    val setmealId: Long? = null,
    val dishFlavor: String? = null,
    var number: Int = 0,
    val amount: Double? = null,
    val createTime: String? = null
) : Parcelable
