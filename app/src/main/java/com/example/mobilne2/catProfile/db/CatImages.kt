package com.example.mobilne2.catProfile.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class CatImages {
    @PrimaryKey  var id: String = ""
    var url: String = ""
}