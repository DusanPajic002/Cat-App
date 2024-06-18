package com.example.mobilne2.catProfile

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.mobilne2.R
import com.example.mobilne2.catProfile.profile.CatProfileState
import com.example.mobilne2.catProfile.profile.CatProfileViewModel
import com.example.mobilne2.catProfile.profile.model.CatProfileUI


@ExperimentalMaterial3Api
fun NavGraphBuilder.catProfileScreen(
    route: String,
    onItemClick: (String) -> Unit,
    onClose: () -> Unit,
) = composable(
    route = route,
) { navBackStackEntry ->

    val catProfileViewModel: CatProfileViewModel = hiltViewModel(navBackStackEntry)
    val state = catProfileViewModel.state.collectAsState()

    CatProfile(
        data = state.value,
        onItemClick = onItemClick,
        onClose = onClose,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatProfile(
    data: CatProfileState,
    onClose: () -> Unit,
    onItemClick: (String) -> Unit,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Profile") },
                navigationIcon = {
                    IconButton(onClick = { onClose() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFFE18C44)
                )
            )
        },
        content = { paddingValues ->
            Image(
                painter = rememberImagePainter(data = R.drawable.image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                if (data.error != null) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        val errorMessage = when (data.error) {
                            is CatProfileState.DetailsError.DataUpdateFailed ->
                                "Failed to load. Error message: ${data.error.cause?.message}."
                        }
                        Text(text = errorMessage)
                    }
                } else if (!data.fetching && data.cat != null) {
                    CatData(
                        cat = data.cat,
                        onItemClick = onItemClick,
                        imageUrl = (data.image?.url ?: "")
                    )
                } else if (data.fetching) {
                    LoadingCatProfile()
                }
            }

        }
    )
}

@Composable
private fun CatData(
    cat: CatProfileUI,
    imageUrl: String,
    onItemClick: (String) -> Unit,
) {
    val scrollState = rememberScrollState()
    Card(
        modifier = Modifier
            .verticalScroll(scrollState)
            .fillMaxSize()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE7E8E4))
    ) {
        Text(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally),
            text = cat.name,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
        )
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = "Loaded image",
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    onItemClick(cat.id)
                }
        )
        Text(
            modifier = Modifier
                .padding(8.dp),
            text = "Rarity:  " + cat.isRare,
            fontSize = 18.sp,
        )
        Text(
            modifier = Modifier
                .padding(8.dp),
            text = "Origin:  " + cat.originCountries.joinToString(", "),
            fontSize = 18.sp,
        )
        Text(
            modifier = Modifier
                .padding(8.dp),
            text = "Size:  " + cat.averageWeight,
            fontSize = 18.sp,
        )
        Text(
            modifier = Modifier
                .padding(8.dp),
            text = "Life span:  " + cat.lifeSpan,
            fontSize = 18.sp,
        )
        Text(
            modifier = Modifier
                .padding(8.dp),
            text = "Personality:  " + cat.temperamentTraits.joinToString(", "),
            fontSize = 18.sp,
        )
        Text(
            modifier = Modifier.padding(8.dp),
            text = "Adaptability: ${cat.adaptability}\n" +
                    "Affection Level: ${cat.affectionLevel}\n" +
                    "Child Friendly: ${cat.childFriendly}\n" +
                    "Dog Friendly: ${cat.dogFriendly}\n" +
                    "Energy Level: ${cat.energyLevel}",
            fontSize = 18.sp
        )
        Text(
            modifier = Modifier
                .padding(8.dp),
            text = "Description:\n" + cat.description,
            fontSize = 18.sp,
        )

        val context = LocalContext.current
        Button(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally)
                .size(120.dp, 40.dp),
            onClick = {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(cat.wikipedia_url))
                context.startActivity(intent)
            },
            contentPadding = PaddingValues(all = 8.dp),
        ) {
            Text(
                text = "Wiki",
                fontSize = 16.sp,
            )
        }

    }
}


@Composable
fun LoadingCatProfile() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Loading cat profile...", fontSize = 24.sp)
            Spacer(modifier = Modifier.height(16.dp))
            CircularProgressIndicator()
        }
    }
}

