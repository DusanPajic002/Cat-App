package com.example.mobilne2.catListP.list.model

import com.example.mobilne2.catListP.mappers.asCatDbModel

data class CatListUI(
    val id: String,
    val name: String,
    val alt_names: String = "",
    val description: String,
    val temperament: List<String>,
    val imperial : String,
    val metric : String
)