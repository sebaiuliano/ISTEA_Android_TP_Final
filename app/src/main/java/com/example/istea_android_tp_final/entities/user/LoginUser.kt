package com.example.istea_android_tp_final.entities.user

data class LoginUser(
    private var username : String,
    private var password : String
) {

    fun getUsername() : String {
        return username
    }

}