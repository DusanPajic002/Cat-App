package com.example.domaci2.catListP.list.model

data class CatListUI(
    val id: String,
    val name: String,
    val alt_names: String = "",
    val description: String,
    val temperament: List<String>,
)