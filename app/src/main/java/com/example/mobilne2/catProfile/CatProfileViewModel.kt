package com.example.domaci2.catProfile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domaci2.catListP.api.model.CatApiModel
import com.example.domaci2.breeds.CatProfileUI
import com.example.domaci2.catListP.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CatProfileViewModel(
    private val catId: String,
    private val repository: Repository = Repository,
) : ViewModel() {

    private val _state = MutableStateFlow(CatProfileState(catId = catId))
    val state = _state.asStateFlow()
    private fun setState(reducer: CatProfileState.() -> CatProfileState) =
        _state.getAndUpdate(reducer)

    init {
        fetchCats()
    }

    private fun fetchCats() {
        viewModelScope.launch {
            setState { copy(fetching = true) }
            try {
                val catt = withContext(Dispatchers.IO) {
                    repository.fetchCat(catId).asCatUiModel()
                }
                Log.d("CATTTA",catt.toString())
                val image = withContext(Dispatchers.IO) {
                    repository.fetchCatImages(catt.reference_image_id)
                }
                setState { copy(cat = catt ) }
                setState { copy(image = image.url ) }
            } catch (error: Exception) {
                setState { copy(error = CatProfileState.DetailsError.DataUpdateFailed(cause = error)) }
            } finally {
                setState { copy(fetching = false) }
            }
        }
    }

    private fun CatApiModel.asCatUiModel() = CatProfileUI(
        alt_names = this.alt_names,
        description = this.description,
        wikipedia_url = this.wikipedia_url,
        name = this.name,
        fullDescription = this.description,
        originCountries = this.origin.split(", "),
        temperamentTraits = this.temperament.split(", "),
        lifeSpan = this.life_span,
        averageWeight = this.weight.metric,
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