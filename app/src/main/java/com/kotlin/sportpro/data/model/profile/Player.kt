package com.kotlin.sportpro.data.model.profile

import com.google.gson.annotations.SerializedName

data class Player(
    @SerializedName("city")
    val city: String,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("organization")
    val organization: String,
    @SerializedName("photo")
    val photo: String,
    @SerializedName("trainer")
    val trainer: Int,
    @SerializedName("user")
    val user: User,
    @SerializedName("weight")
    val weight: Int
)