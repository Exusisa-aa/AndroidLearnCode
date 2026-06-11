package com.restaurant.app.data.model

data class Page<T>(
    val records: List<T>,
    val total: Long,
    val size: Long,
    val current: Long,
    val orders: List<Any>? = null,
    val optimizeCountSql: Boolean = true,
    val searchCount: Boolean = true,
    val countId: String? = null,
    val maxLimit: Long? = null,
    val pages: Long = 0
)
