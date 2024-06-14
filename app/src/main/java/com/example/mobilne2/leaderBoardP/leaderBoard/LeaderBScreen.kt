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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


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
        eventPublisher = {
            leaderBViewModel.setEvent(it)
        },
        onClose = onClose,
    )
}
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeaderBScreen(
    data: LeaderBState,
    eventPublisher: (LeaderBState.Events) -> Unit,
    onClose: () -> Unit,
) {

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
                if(data.error != null) {
                    when(data.error) {
                        is LeaderBState.Error.LeaderBoardFailed -> {
                            Text(text = "Error to load leaderboard.", fontSize = 24.sp)
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(onClick = {
                                onClose()
                            },
                                modifier = Modifier.padding(8.dp).size(145.dp, 47.dp)
                            ){
                                Text(text = "Go to Home")
                            }
                        }
                    }
                } else if (!data.fetching) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = paddingValues.calculateTopPadding())
                            .background(Color(0xFFF0F0FF))
                            .padding(bottom = 60.dp),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        pageChanger(
                            data = data,
                            eventPublisher = eventPublisher
                        )

                        LazyColumn(
                            modifier = Modifier
                                .background(Color(0xFFF0F0FF))
                        ) {
                            val groupedData = data.leaderBoardOnlinePerPage.groupBy { it.category }
                            groupedData.forEach { (category, items) ->
                                item {
                                    val categoryName = when (category) {
                                        1 -> "Guess the Fact"
                                        2 -> "Guess the Cat"
                                        3 -> "Left or Right Cat"
                                        else -> "Unknown"
                                    }
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
                                    Spacer(modifier = Modifier.height(14.dp))
                                }
                                items(items.size) { index ->
                                    val player = items[index]
                                    println(player.createdAt)
                                    println("-----------------")
                                    val date = Date(player.createdAt)
                                    val format =
                                        SimpleDateFormat("dd.MM.yyyy. | HH:mm", Locale.getDefault())
                                    val formattedTime = format.format(date)
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
                                            val rank =
                                                index + 1 + (data.page - 1) * data.dataPerPage
                                            Text(
                                                text = "${rank}. ${player.nickname}",
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 18.sp
                                            )
                                            Text(
                                                text = formattedTime,
                                                fontSize = 12.sp,
                                                color = Color.Gray
                                            )
                                        }
                                        Text(
                                            text = player.result.toString(),
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 18.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
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
        )
}

@Composable
private fun pageChanger(
    data: LeaderBState,
    eventPublisher: (LeaderBState.Events) -> Unit
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
                    eventPublisher(LeaderBState.Events.changePage(data.page - 1))
                }
            },
            enabled = data.page > 1
        ) {
            Text("<---", fontSize = 20.sp)
        }
        Button(
            onClick = {
                if (data.page < data.maxPage)
                    eventPublisher(LeaderBState.Events.changePage(data.page + 1))
            },
            enabled = data.page < data.maxPage
        ) {
            Text("--->", fontSize = 20.sp)
        }
    }
}
