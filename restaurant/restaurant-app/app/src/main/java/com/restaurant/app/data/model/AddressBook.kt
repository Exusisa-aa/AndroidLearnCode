package com.restaurant.app.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AddressBook(
    val id: Long? = null,
    val userId: Long? = null,
    val consignee: String? = null,
    val phone: String? = null,
    val sex: String? = null, // "0": Female, "1": Male
    val provinceCode: String? = null,
    val provinceName: String? = null,
    val cityCode: String? = null,
    val cityName: String? = null,
    val districtCode: String? = null,
    val districtName: String? = null,
    val detail: String? = null,
    val label: String? = null,
    val isDefault: Int? = 0, // 0: No, 1: Yes
    val isDeleted: Int? = 0
) : Parcelable
