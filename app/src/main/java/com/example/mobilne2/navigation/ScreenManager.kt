package com.example.mobilne2.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.mobilne2.catListP.list.catListScreen
import com.example.mobilne2.catProfile.aGallery.gallery.catGaleryScreen
import com.example.mobilne2.catProfile.catProfileScreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenManager() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "cats",
    ) {
        catListScreen(
            route = "cats",
            onItemClick = {
                navController.navigate(route = "cat/${it}")
            },
        )
        catProfileScreen(
            route = "cat/{catId}",
            onItemClick = {
                navController.navigate(route = "catGalery/${it}")
            },
            onClose = {
                navController.navigateUp()
            },

        )
        catGaleryScreen(
            route = "catGalery/{catProfileId}",
            onItemClick = {
                navController.navigate(route = "photo/${it}")
            },
            onClose = {
                navController.navigateUp()
            },
        )

    }

}
inline val SavedStateHandle.catId: String
    get() = checkNotNull(get("catId")) { "catId is mandatory" }
inline val SavedStateHandle.catProfileId: String
    get() = checkNotNull(get("catProfileId")) { "catProfileId is mandatory" }