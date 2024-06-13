package com.example.mobilne2.leaderBoardP.leaderBoard

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter


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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeaderBScreen(
    data: LeaderBState,
    onClose: () -> Unit,
) {
    if (!data.fetching) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text("LeaderBoard", color = Color.White) },
                    navigationIcon = {
                        IconButton(onClick = { onClose() }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.White
                            )
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color(0xFFE18C44)
                    )
                )
            },
            content = { paddingValues ->
                val groupedData = data.leaderBoardOnline.groupBy { it.category }
                LazyColumn(
                    contentPadding = paddingValues,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFF0F0F0))
                ) {
                    groupedData.forEach { (category, items) ->
                        item {
                            val categoryName = when (category) {
                                1 -> "Guess the Fact"
                                2 -> "Guess the Cat"
                                3 -> "Left or Right Cat"
                                else -> "Unknown"
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = categoryName,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color(0xFFf5d749))
                                    .padding(16.dp),
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                        }
                        items(items.size) { index ->
                            val item = items[index]
                            val createdAtDateTime = LocalDateTime.ofInstant(
                                Instant.ofEpochSecond(item.createdAt),
                                ZoneId.systemDefault()
                            )
                            val formattedDate = createdAtDateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy. | HH:mm"))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(14.dp)
                                    .background(Color.White)
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = "${index + 1}. ${item.nickname}",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp)
                                    Text(
                                        text = formattedDate,
                                        fontSize = 12.sp,
                                        color = Color.Gray)
                                }
                                Text(
                                    text = item.result.toString(),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp)
                            }

                        }
                    }
                }
            }
        )
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "Loading LeaderBoard...", fontSize = 24.sp)
                Spacer(modifier = Modifier.height(16.dp))
                CircularProgressIndicator()
            }
        }
    }
}
