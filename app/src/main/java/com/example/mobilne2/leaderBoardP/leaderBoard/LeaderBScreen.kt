package com.example.mobilne2.leaderBoardP.leaderBoard

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.mobilne2.leaderBoardP.leaderBoard.model.LeaderBoardUI


fun NavGraphBuilder.leaderBScreen(
    route: String,
    onClose: () -> Unit,
) = composable(
    route = route,
) { navBackStackEntry ->

    val leaderBViewModel: LeaderBViewModel = hiltViewModel(navBackStackEntry)
    val state = leaderBViewModel.state.collectAsState()

    LeaderBScreen(
        data = state.value,
        onClose = onClose,
    )
}

@Composable
fun LeaderBScreen(
    data: LeaderBState,
    onClose: () -> Unit,
) {
    if (data.fetching) {
        // Prikazi loader
    } else if (data.error != null) {
        // Prikazi gresku
    } else {
        LeaderBoardList(
            data.leaderBoard,
            onClose = onClose
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LeaderBoardList(
    items: List<LeaderBoardUI>,
    onClose: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("LeaderBoard") },
                navigationIcon = {
                    IconButton(onClick = onClose) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        content = {
            LazyColumn {
                items(items.size) { index ->
                    val item = items[index]
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = item.createdAt.toString())
                        Text(text = item.nickname)
                        Text(text = item.result.toString())
                        Text(text = item.createdAt)
                    }
                }
            }
        }
    )
}
