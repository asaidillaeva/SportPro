package com.kotlin.sportpro.data.model.login

import com.google.gson.annotations.SerializedName

data class LoginRequestBody (
    @SerializedName("user")
    val user: UserLogin
)


data class UserLogin(
    val phone: String
)