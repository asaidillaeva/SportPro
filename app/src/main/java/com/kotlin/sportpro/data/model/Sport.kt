package com.kotlin.sportpro.data.model

import com.google.gson.annotations.SerializedName

data class Sport(

    @SerializedName("category")
    val category: Category?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("is_ourchoiсe")
    val isOurchoiсe: Boolean?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("photo")
    val photo: String?,
    @SerializedName("short_desc")
    val short_desc: String?)