package com.example.mobilne2.catProfile.aGallery.gallery

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.mobilne2.catProfile.aGallery.aPhoto.CatPhotoState
import com.example.mobilne2.catProfile.profile.model.CatImageUI
import com.example.mobilne2.core.compose.PhotoPreview
import com.example.mobilne2.leaderBoardP.leaderBoard.LeaderBState

fun NavGraphBuilder.catGalleryScreen(
    route: String,
    onItemClick: (String) -> Unit,
    onClose: () -> Unit,
) = composable(
    route = route,
) { navBackStackEntry ->

    val catGalleryViewModel: CatGalleryViewModel = hiltViewModel(navBackStackEntry)

    val state = catGalleryViewModel.state.collectAsState()

    CatGalleryScreen(
        data = state.value,
        onItemClick = onItemClick,
        onClose = onClose,
        eventPublisher = {
            catGalleryViewModel.setEvent(it)
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatGalleryScreen(
    data: CatGalleryState,
    onItemClick: (String) -> Unit,
    onClose: () -> Unit,
    eventPublisher: (CatGalleryState.Events) -> Unit
) {

    Scaffold(
        topBar = {
            Column {
                CenterAlignedTopAppBar(
                    title = { Text("Cat Gallery") },
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
            }
        },
        content = { paddingValues ->
            BoxWithConstraints(
                modifier = Modifier.padding(paddingValues),
                contentAlignment = Alignment.BottomCenter,
            ) {
                val screenWidth = this.maxWidth
                val cellSize = (screenWidth / 2) - 4.dp

                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    if (data.error != null && data.error is CatGalleryState.Error.LoadError) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = data.error.message,
                                    fontSize = 24.sp,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(16.dp),
                                    overflow = TextOverflow.Ellipsis,
                                )
                                Button(
                                    onClick = { onClose() }
                                ) {
                                    Text("Go back")
                                }
                            }
                    } else if (!data.fetching){
                        pageChanger(data, eventPublisher)
                        LazyVerticalGrid(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 4.dp),
                            columns = GridCells.Fixed(2),
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                        ) {
                            itemsIndexed(data.catsPerPage) { index, cat ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .size(cellSize)
                                        .clickable {
                                            onItemClick(cat.id)
                                        }
                                ) {
                                    PhotoPreview(
                                        modifier = Modifier.fillMaxSize(),
                                        url = cat.url,
                                        title = cat.id,
                                    )
                                }
                            }
                        }
                    }else{
                        LoadingGallery()
                    }
                }
            }
        },
    )
}


@Composable
private fun pageChanger(
    data: CatGalleryState,
    eventPublisher: (CatGalleryState.Events) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .padding(start = 6.dp, end = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = {
                if (data.page > 1) {
                    eventPublisher(CatGalleryState.Events.changePage(data.page - 1))
                }
            },
            enabled = data.page > 1
        ) {
            Text("<---", fontSize = 20.sp)
        }
        Button(
            onClick = {
                if (data.page < data.maxPage)
                    eventPublisher(CatGalleryState.Events.changePage(data.page + 1))
            },
            enabled = data.page < data.maxPage
        ) {
            Text("--->", fontSize = 20.sp)
        }
    }
}

@Composable
fun LoadingGallery() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Loading gallery...", fontSize = 24.sp)
            Spacer(modifier = Modifier.height(16.dp))
            CircularProgressIndicator()
        }
    }
}