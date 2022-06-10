package com.kotlin.sportpro.data.model.grid


import com.google.gson.annotations.SerializedName

data class Judge(
    @SerializedName("document")
    val document: Any?,
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