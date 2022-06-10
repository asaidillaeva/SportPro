package com.kotlin.sportpro.data.model


import com.google.gson.annotations.SerializedName

data class Status(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)