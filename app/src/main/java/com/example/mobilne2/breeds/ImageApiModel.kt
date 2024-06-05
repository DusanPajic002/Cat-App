package com.example.domaci2.breeds

import kotlinx.serialization.Serializable

@Serializable
data class ImageApiModel (
    val id: String,
    val url: String
)