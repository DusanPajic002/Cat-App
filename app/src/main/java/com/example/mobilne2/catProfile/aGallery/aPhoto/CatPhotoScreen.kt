package com.example.mobilne2.catProfile.aGallery.aPhoto

import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.mobilne2.core.compose.AppIconButton
import com.example.mobilne2.core.compose.PhotoPreview

fun NavGraphBuilder.catPhotoScreen(
    route: String,
    onClose: () -> Unit,
) = composable(
    route = route,
    enterTransition = { slideInVertically { it } },
    popExitTransition = { slideOutVertically { it } },
) { navBackStackEntry ->

    val photoGalleryViewModel = hiltViewModel<CatPhotoViewModel>(navBackStackEntry)
    val state = photoGalleryViewModel.state.collectAsState()

    PhotoGalleryScreen(
        state = state.value,
        onClose = onClose,
    )
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PhotoGalleryScreen(
    state: CatPhotoState,
    onClose: () -> Unit,
) {
    val pagerState = rememberPagerState(
        pageCount = { state.photos.size }
    )

    LaunchedEffect(state.photos, state.imageIndex) {
        if (state.photos.isNotEmpty()) {
            state.imageIndex.let { pagerState.scrollToPage(it.toInt()) }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = {"Photos"},

            navigationIcon = {
                AppIconButton(
                    imageVector = Icons.Default.ArrowBack,
                    onClick = onClose,
                )
            })
        },
        content = { paddingValues ->
            if (state.photos.isNotEmpty()) {
                HorizontalPager(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = paddingValues,
                    pageSize = PageSize.Fill,
                    pageSpacing = 16.dp,
                    state = pagerState,
                    key = { state.photos[it].id }
                ) { pageIndex ->
                    val image = state.photos[pageIndex]
                    PhotoPreview(
                        modifier = Modifier,
                        url = image.url,
                        title = image.id,
                        showTitle = false,
                    )
                }
            } else {
                Text(
                    modifier = Modifier.fillMaxSize(),
                    text = "No images.",
                )
            }
        },
    )
}
