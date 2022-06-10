package com.kotlin.sportpro.data.model

import com.kotlin.sportpro.data.model.grid.Matche

data class Tournament(
    val hasNext: Boolean,
    val hasPrevious: Boolean,
    val stage: String,
    val match1: Matche,
    val match2: Matche? = null
)