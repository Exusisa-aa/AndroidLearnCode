package com.restaurant.app.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: Long? = null,
    val name: String? = null,
    val phone: String? = null,
    val sex: String? = null,
    val idNumber: String? = null,
    val avatar: String? = null,
    val status: Int? = null,
    val code: String? = null // For login
) : Parcelable
