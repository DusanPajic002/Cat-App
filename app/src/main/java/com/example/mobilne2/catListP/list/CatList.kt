package com.example.mobilne2.catListP.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import coil.compose.rememberImagePainter
import com.example.mobilne2.R
import com.example.mobilne2.catListP.list.model.CatListUI

@ExperimentalMaterial3Api
fun NavGraphBuilder.catListScreen(
    route: String,
    onItemClick: (String) -> Unit,
    onClose: () -> Unit
) = composable(
    route = route
) {
    val catListViewModel = hiltViewModel<CatListViewModel>()
    val state by catListViewModel.state.collectAsState()

    CatList(
        data = state,
        eventPublisher = { catListViewModel.setEvent(it) },
        onItemClick = onItemClick,
        onClose = onClose,
    )
}

@ExperimentalMaterial3Api
@Composable
fun CatList(
    data: CatListState,
    eventPublisher: (CatListState.FilterEvent) -> Unit,
    onItemClick: (String) -> Unit,
    onClose: () -> Unit,
) {
    Scaffold(
        topBar = {
            Column {
                CenterAlignedTopAppBar(
                    title = { Text(text = "CatList", color = Color.White) },
                    navigationIcon = {
                        IconButton(onClick = { onClose() }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.White,
                            )
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color(0xFFE18C44))
                )
            }
        },
        content = {
            Image(
                painter = rememberImagePainter(data = R.drawable.image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            when (data.fetching) {
                true -> LoadingCatList()
                false -> {
                    if (data.error != null) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center,
                        ) {
                            val errorMessage = when (data.error) {
                                is CatListState.ListError.ListUpdateFailed ->
                                    "Failed to load. Error message: ${data.error.cause?.message}."
                            }
                            Text(text = errorMessage)
                        }
                    } else {
                        CatsList(
                            paddingValues = it,
                            data = data.filteredCats,
                            textfilt = data.filter,
                            eventPublisher = eventPublisher,
                            onItemClick = {
                                onItemClick(it)
                            },
                        )
                    }
                }
            }
        }
    )
}

@Composable
private fun CatsList(
    data: List<CatListUI>,
    paddingValues: PaddingValues,
    eventPublisher: (CatListState.FilterEvent) -> Unit,
    textfilt: String,
    onItemClick: (String) -> Unit,
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier.fillMaxSize().padding(paddingValues).clickable { focusManager.clearFocus() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        filterTextField(eventPublisher, textfilt)

        Button(
            onClick = {
                focusManager.clearFocus()
                eventPublisher(CatListState.FilterEvent.filterClick)
            },
            modifier = Modifier.width(150.dp).padding(bottom = 14.dp),
        ) { Text("Filter") }

        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
            modifier = Modifier.fillMaxSize().padding(horizontal = 4.dp),
            contentPadding = PaddingValues(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(data) { cat -> CatListItem( data = cat, onItemClick = onItemClick ) }
        }
    }
}


@Composable
private fun CatListItem(
    data: CatListUI,
    onItemClick: (String) -> Unit,
) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp).padding(vertical = 6.dp).clickable { onItemClick(data.id) },
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Text(
            modifier = Modifier.padding(all = 16.dp),
            text = data.name,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 18.sp,
        )
        Row {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp).padding(bottom = 8.dp).weight(weight = 1f),
                text = data.alt_names,
            )
        }
        Row {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp).padding(bottom = 8.dp).weight(weight = 1f),
                text = if (data.description.length > 100)
                    data.description.substring(0, 100) + "..."
                else data.description,
            )
            Icon(
                modifier = Modifier.padding(end = 16.dp),
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = null,
            )
        }
        Row( modifier = Modifier.padding(horizontal = 16.dp).padding(bottom = 8.dp) ) { SuggestionChipExample(data.temperament) }
    }
}

@Composable
fun SuggestionChipExample(personalityTraits: List<String>) {
    val randomTraits = remember { personalityTraits.shuffled().take(3) }
    randomTraits.forEach { trait ->
        SuggestionChip(
            modifier = Modifier.padding(end = 4.dp),
            onClick = {},
            label = { Text(trait) },
        )
    }
}

@Composable
fun LoadingCatList() {
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

@Composable
private fun filterTextField(
    eventPublisher: (CatListState.FilterEvent) -> Unit,
    textfilt: String
){
    TextField(
        value = textfilt,
        onValueChange = { eventPublisher(CatListState.FilterEvent.filterEvent(it)) },
        colors = TextFieldDefaults.colors( unfocusedContainerColor = Color(0xFFEAEAEA), focusedContainerColor = Color(0xFFEAEAEA), focusedIndicatorColor = Color.Gray,
            unfocusedPlaceholderColor = Color.Gray, focusedPlaceholderColor = Color.Gray, cursorColor = Color.Black ),
        label = { Text("Filter Cats") },
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp).padding(bottom = 2.dp).padding(top = 8.dp),
        trailingIcon = {
            if (textfilt.isNotEmpty()) {
                IconButton(onClick = {
                    eventPublisher(CatListState.FilterEvent.filterEvent(""))
                    eventPublisher(CatListState.FilterEvent.filterClick)
                }) { Icon(Icons.Default.Clear, contentDescription = "Clear text") }
            }
        }
    )
}