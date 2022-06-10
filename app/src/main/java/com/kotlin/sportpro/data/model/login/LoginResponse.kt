package com.kotlin.sportpro.data.model.login

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("user")
    val user: UserResponse
)

data class UserResponse(
    val token: String
)