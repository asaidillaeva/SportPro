package com.kotlin.sportpro.data.model.profile

import com.google.gson.annotations.SerializedName
import com.kotlin.sportpro.data.model.Region


data class Trainer (
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name : String,
    @SerializedName("surname") val surname : String,
    @SerializedName("middlename") val middlename : String,
    @SerializedName("phone") val phone : String,
    @SerializedName("region") val region : Region,
    @SerializedName("sport") val sport : String
)