package com.kotlin.sportpro.data.model

import com.google.gson.annotations.SerializedName



data class Creator (
    @SerializedName("age")
    val age: Int,
    @SerializedName("document")
    val document: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("middlename")
    val middlename: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("organization")
    val organization: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("photo")
    val photo: Any?,
    @SerializedName("region")
    val region: Int,
    @SerializedName("role")
    val role: Int,
    @SerializedName("sport")
    val sport: Int,
    @SerializedName("surname")
    val surname: String
)