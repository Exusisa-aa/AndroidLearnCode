package com.restaurant.app.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Orders(
    var id: Long? = null,
    var number: String? = null,
    var status: Int? = null,
    var userId: Long? = null,
    var addressBookId: Long? = null,
    var orderTime: String? = null,
    var checkoutTime: String? = null,
    var payMethod: Int? = null,
    var amount: Double? = null,
    var remark: String? = null,
    var userName: String? = null,
    var phone: String? = null,
    var address: String? = null,
    var consignee: String? = null,
    var orderDetails: List<OrderDetail>? = null
) : Parcelable

@Parcelize
data class OrderDetail(
    var id: Long? = null,
    var name: String? = null,
    var image: String? = null,
    var orderId: Long? = null,
    var dishId: Long? = null,
    var setmealId: Long? = null,
    var dishFlavor: String? = null,
    var number: Int? = null,
    var amount: Double? = null
) : Parcelable
