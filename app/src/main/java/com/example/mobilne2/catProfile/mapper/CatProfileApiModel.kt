package com.example.mobilne2.catProfile.mapper

import com.example.mobilne2.catListP.db.Cat
import com.example.mobilne2.catProfile.profile.model.CatProfileUI

fun Cat.asCatUiModel(): CatProfileUI{
    return CatProfileUI(
        alt_names = this.alt_names,
        name = this.name,
        description = this.description,
        fullDescription = this.description,
        temperamentTraits = this.temperament.split(", "),
        wikipedia_url = this.wikipedia_url,
        originCountries = this.origin.split(", "),
        lifeSpan = this.life_span,
        adaptability = this.adaptability,
        affectionLevel = this.affection_level,
        childFriendly = this.child_friendly,
        dogFriendly = this.dog_friendly,
        energyLevel = this.energy_level,
        grooming = this.grooming,
        healthIssues = this.health_issues,
        intelligence = this.intelligence,
        sheddingLevel = this.shedding_level,
        socialNeeds = this.social_needs,
        strangerFriendly = this.stranger_friendly,
        vocalisation = this.vocalisation,
        reference_image_id = this.reference_image_id?:"",
        isRare = this.rare == 1,
    )
}