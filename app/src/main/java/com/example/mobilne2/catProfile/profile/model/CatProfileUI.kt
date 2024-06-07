package com.example.mobilne2.catProfile.profile.model


data class CatProfileUI (
    val alt_names: String = "",
    val description: String,
    val wikipedia_url: String? = null,
    val name: String,
    val fullDescription: String,
    val originCountries: List<String>,
    val temperamentTraits: List<String>,
    val lifeSpan: String,
    //val averageWeight: String,
    val adaptability: Int,
    val affectionLevel: Int,
    val childFriendly: Int,
    val dogFriendly: Int,
    val energyLevel: Int,
    val grooming: Int,
    val healthIssues: Int,
    val intelligence: Int,
    val sheddingLevel: Int,
    val socialNeeds: Int,
    val strangerFriendly: Int,
    val vocalisation: Int,
    val reference_image_id: String,
    val isRare: Boolean,
    //val imageUrl: String,
)