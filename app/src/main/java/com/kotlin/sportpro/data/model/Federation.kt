package com.kotlin.sportpro.data.model


import com.google.gson.annotations.SerializedName

data class Federation(
    @SerializedName("admin")
    val admin: Admin?,
    @SerializedName("contacts")
    val contacts: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("logo")
    val logo: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("sport")
    val sport: Sport?
)