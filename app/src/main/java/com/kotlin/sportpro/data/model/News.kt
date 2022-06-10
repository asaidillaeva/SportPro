package com.kotlin.sportpro.data.model

import com.google.gson.annotations.SerializedName
import com.kotlin.sportpro.data.model.profile.User

data class News(

    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("article") val article: String,
    @SerializedName("author") val author: Author,
    @SerializedName("photo") val photo: String,
    @SerializedName("dateofadd") val dateofadd: String,
    @SerializedName("sport") val sport: Sport

)


data class Author(

    @SerializedName("id") val id: Int,
    @SerializedName("user") val user: User
)
