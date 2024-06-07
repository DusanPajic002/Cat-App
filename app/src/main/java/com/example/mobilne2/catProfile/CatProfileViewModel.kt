package com.example.mobilne2.catProfile

import androidx.lifecycle.ViewModel
import com.example.mobilne2.catProfile.repository.CatProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import javax.inject.Inject

class CatProfileViewModel @Inject constructor(
    private val catId: String,
    private val repository: CatProfileRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(CatProfileState(catId = catId))
    val state = _state.asStateFlow()
    private fun setState(reducer: CatProfileState.() -> CatProfileState) =
        _state.getAndUpdate(reducer)

    init {
        //fetchCats()
    }

//    private fun fetchCats() {
//        viewModelScope.launch {
//            setState { copy(fetching = true) }
//            try {
//                val catt = withContext(Dispatchers.IO) {
//                    repository.fetchCat(catId)
//                }
//                Log.d("CATTTA",catt.toString())
//                val image = withContext(Dispatchers.IO) {
//                    repository.fetchCatImages(catt.reference_image_id)
//                }
//                setState { copy(cat = catt ) }
//                setState { copy(image = image.url ) }
//            } catch (error: Exception) {
//                setState { copy(error = CatProfileState.DetailsError.DataUpdateFailed(cause = error)) }
//            } finally {
//                setState { copy(fetching = false) }
//            }
//        }
//    }

   /* private fun CatApiModel.asCatUiModel() = CatProfileUI(
        alt_names = this.alt_names,
        name = this.name,
        description = this.description,
        fullDescription = this.description,
        temperamentTraits = this.temperament.split(", "),
//        wikipedia_url = this.wikipedia_url,
//        originCountries = this.origin.split(", "),
//        lifeSpan = this.life_span,
//        //averageWeight = this.weight.metric,
//        adaptability = this.adaptability,
//        affectionLevel = this.affection_level,
//        childFriendly = this.child_friendly,
//        dogFriendly = this.dog_friendly,
//        energyLevel = this.energy_level,
//        grooming = this.grooming,
//        healthIssues = this.health_issues,
//        intelligence = this.intelligence,
//        sheddingLevel = this.shedding_level,
//        socialNeeds = this.social_needs,
//        strangerFriendly = this.stranger_friendly,
//        vocalisation = this.vocalisation,
//        reference_image_id = this.reference_image_id?:"",
//        isRare = this.rare == 1,
    )
*/
}