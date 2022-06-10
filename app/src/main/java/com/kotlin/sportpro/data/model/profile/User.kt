package com.kotlin.sportpro.data.model.profile


import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("name")
    val name: String,
    @SerializedName("surname")
    val surname: String,
    @SerializedName("middlename")
    val middlename: String,
    @SerializedName("photo")
    val photo: Any?,
    @SerializedName("age")
    val age: Int,
    @SerializedName("sex")
    var sex: String? = "Мужчина",
    @SerializedName("phone")
    val phone: String,
    @SerializedName("role")
    val role: Int,
    @SerializedName("region")
    val region: Int,
    @SerializedName("sport")
    val sport: Int,
    @SerializedName("organization")
    val organization: String,
    @SerializedName("document")
    val document: String?

)