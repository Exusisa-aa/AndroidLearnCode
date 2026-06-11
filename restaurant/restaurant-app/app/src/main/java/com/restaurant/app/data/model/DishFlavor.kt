package com.restaurant.app.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DishFlavor(
    val id: Long,
    val dishId: Long,
    val name: String,
    val value: String?
) : Parcelable
