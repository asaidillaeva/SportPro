package com.kotlin.sportpro.data.model.grid


import com.google.gson.annotations.SerializedName

data class Winner(
    @SerializedName("age")
    val age: Int?,
    @SerializedName("contact")
    val contact: String?,
    @SerializedName("dateofadd")
    val dateofadd: String?,
    @SerializedName("dateofbirth")
    val dateofbirth: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("middlename")
    val middlename: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("organization")
    val organization: String?,
    @SerializedName("photo")
    val photo: String?,
    @SerializedName("playercategory")
    val playercategory: Int?,
    @SerializedName("score")
    val score: Int?,
    @SerializedName("sex")
    val sex: String?,
    @SerializedName("sport")
    val sport: Int?,
    @SerializedName("surname")
    val surname: String?,
    @SerializedName("trainer")
    val trainer: Int?,
    @SerializedName("weight")
    val weight: Int?
)