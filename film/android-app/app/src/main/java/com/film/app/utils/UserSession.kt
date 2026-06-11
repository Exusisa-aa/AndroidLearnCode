package com.film.app.utils

import com.film.app.data.model.User

object UserSession {
    var user: User? = null
    
    fun isLoggedIn(): Boolean = user != null
    
    fun logout() {
        user = null
    }
}
