package com.example.mobilne2.catListP.mappers

import com.example.mobilne2.catListP.api.model.CatApiModel
import com.example.mobilne2.catListP.db.Cat
import com.example.mobilne2.catListP.list.model.CatListUI

fun CatApiModel.asCatDbModel(): Cat {
    return Cat(
        id = this.id,
        name = this.name,
        description = this.description,
        alt_names = this.alt_names,
        temperament = this.temperament,
        cfa_url = this.cfa_url,
        vetstreet_url = this.vetstreet_url,
        vcahospitals_url = this.vcahospitals_url,
        origin = this.origin,
        country_codes = this.country_codes,
        country_code = this.country_code,
        life_span = this.life_span,
        indoor = this.indoor,
        lap = this.lap,
        adaptability = this.adaptability,
        affection_level = this.affection_level,
        child_friendly = this.child_friendly,
        dog_friendly = this.dog_friendly,
        energy_level = this.energy_level,
        grooming = this.grooming,
        health_issues = this.health_issues,
        intelligence = this.intelligence,
        shedding_level = this.shedding_level,
        social_needs = this.social_needs,
        stranger_friendly = this.stranger_friendly,
        vocalisation = this.vocalisation,
        experimental = this.experimental,
        hairless = this.hairless,
        natural = this.natural,
        rare = this.rare,
        rex = this.rex,
        suppressed_tail = this.suppressed_tail,
        short_legs = this.short_legs,
        wikipedia_url = this.wikipedia_url,
        hypoallergenic = this.hypoallergenic,
        reference_image_id = this.reference_image_id,
        url = this.url,
    )
}

fun Cat.asCatModel(): CatListUI {
    return CatListUI(
        id = this.id,
        name = this.name,
        description = this.description,
        alt_names = this.alt_names,
        temperament = this.temperament.split(", ")
        )
}


/*

 */