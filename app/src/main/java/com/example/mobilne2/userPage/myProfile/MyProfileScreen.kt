package com.example.mobilne2.userPage.myProfile

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
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

fun NavGraphBuilder.myProfileScreen(
    route: String,
    onClose: () -> Unit,
    onEditProfileClick: () -> Unit,
) = composable(
    route = route,
) { navBackStackEntry ->

    val myProfileViewModel: MyProfileViewModel = hiltViewModel(navBackStackEntry)
    val state = myProfileViewModel.state.collectAsState()

    MyProfileScreen(
        data = state.value,
        onEditProfileClick = onEditProfileClick,
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
    onEditProfileClick: () -> Unit,
    onClose: () -> Unit,
    eventPublisher: (MyProfileState.Events) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .clickable { focusManager.clearFocus() },
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
            if (data.error != null) {
                when (data.error) {
                    is MyProfileState.Error.LoadingFailed -> {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Profile not found",
                                color = Color.Red,
                                fontSize = 24.sp
                            )
                        }
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        Text(
                            text = "Name: ${data.user?.firstName} ${data.user?.lastName}",
                            fontSize = 20.sp,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                    item {
                        Text(
                            text = "Email: ${data.user?.email}",
                            fontSize = 20.sp,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                    item {
                        Button(
                            onClick = onEditProfileClick,
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text("Edit profile")
                        }
                    }
                }
            }
        }
    )
}
