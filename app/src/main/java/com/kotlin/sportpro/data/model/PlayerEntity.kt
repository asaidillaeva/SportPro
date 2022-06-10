package com.kotlin.sportpro.data.model
import com.google.gson.annotations.SerializedName
data class PlayerEntity(
    @SerializedName("age")
    val age: Int?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("middlename")
    val middlename: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("organization")
    val organization: String?,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("region")
    val region: Int?,
    @SerializedName("role")
    val role: Int?,
    @SerializedName("sport")
    val sport: Int?,
    @SerializedName("surname")
    val surname: String?
)