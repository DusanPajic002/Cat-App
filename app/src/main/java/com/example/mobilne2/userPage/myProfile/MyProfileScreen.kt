package com.example.mobilne2.userPage.myProfile

import androidx.compose.foundation.background
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalFocusManager
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun NavGraphBuilder.myProfileScreen(
    route: String,
    onClose: () -> Unit,
    onItemCLick: () -> Unit,
) = composable(
    route = route,
) { navBackStackEntry ->

    val myProfileViewModel: MyProfileViewModel = hiltViewModel(navBackStackEntry)
    val state = myProfileViewModel.state.collectAsState()

    MyProfileScreen(
        data = state.value,
        onItemCLick = onItemCLick,
        onClose = onClose,
        eventPublisher = {
            myProfileViewModel.setEvent(it)
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyProfileScreen(
    data: MyProfileState,
    onItemCLick: () -> Unit,
    onClose: () -> Unit,
    eventPublisher: (MyProfileState.Events) -> Unit,
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("MyProfile", color = Color.White) },
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
            if (data.error != null) {
                when (data.error) {
                    is MyProfileState.Error.LoadingFailed -> {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = data.error.message,
                                color = Color.Red,
                                fontSize = 24.sp
                            )
                        }
                    }
                }
            } else if (!data.loading && data.user != null) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(),
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(14.dp)
                                .fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier
                                    .align(Alignment.TopStart)
                                    .padding(end = 48.dp),
                                verticalArrangement = Arrangement.Top,
                                horizontalAlignment = Alignment.Start
                            ) {

                                val name = data.user.firstName
                                val surname = data.user.lastName
                                val nickname = data.user.nickname
                                val email = data.user.email

                                userInfoRow(text = "Name: $name")
                                userInfoRow(text = "Surname: $surname")
                                userInfoRow(text = "Nickname: $nickname")
                                userInfoRow(text = "E-mail: $email")

                            }
                            IconButton(
                                onClick = onItemCLick,
                                modifier = Modifier.align(Alignment.TopEnd)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Edit",
                                    tint = Color.Black
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    LeaderBoardList(
                        data = data,
                        eventPublisher = eventPublisher
                    )
                }
            }else{
                LoadingMyProfile()
            }
        }
    )
}

@Composable
private fun userInfoRow(
    text: String
){
    Text(
        text = text,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Black,
        modifier = Modifier.padding(bottom = 4.dp)
    )
}

@Composable
private fun LeaderBoardList(
    data: MyProfileState,
    eventPublisher: (MyProfileState.Events) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .padding(start = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    if (data.page > 1) {
                        eventPublisher(MyProfileState.Events.changePage(data.page - 1))
                    }
                },
                enabled = data.page > 1
            ) {
                Text("<---", fontSize = 20.sp)
            }
            Button(
                onClick = {
                    if (data.page < data.maxPage)
                        eventPublisher(MyProfileState.Events.changePage(data.page + 1))
                },
                enabled = data.page < data.maxPage
            ) {
                Text("--->", fontSize = 20.sp)
            }
        }
        LazyColumn(
            modifier = Modifier
                .background(Color(0xFFF0F0FF))
        ) {
            val groupedData = data.usersPerPage.groupBy { it.category }
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
                items(items.size) { i ->
                    val player = items[i]
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
                            val rank = i + 1 + (data.page - 1) * data.dataPerPage
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
}


@Composable
fun LoadingMyProfile() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Loading profile...", fontSize = 24.sp)
            Spacer(modifier = Modifier.height(16.dp))
            CircularProgressIndicator()
        }
    }
}