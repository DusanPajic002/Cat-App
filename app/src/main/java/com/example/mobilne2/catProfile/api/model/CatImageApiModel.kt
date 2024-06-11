package com.example.mobilne2.catProfile.api.model

import kotlinx.serialization.Serializable

@Serializable
data class CatImageApiModel(
    val id: String,
    val url: String,
)
