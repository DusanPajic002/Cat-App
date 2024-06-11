package com.example.mobilne2.catProfile.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CatImages (
    @PrimaryKey  var id: String = "",
    var catId: String = "",
    var url: String = ""
)