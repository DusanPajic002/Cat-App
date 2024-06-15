package com.example.mobilne2.userPage.registration

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.registerScreen(
    route: String,
    onItemClick: () -> Unit,
) = composable(
    route = route,
) { navBackStackEntry ->

    val userViewModel: UserViewModel = hiltViewModel(navBackStackEntry)
    val state = userViewModel.state.collectAsState()

    RegisterScreen(
        data = state.value,
        onItemClick = onItemClick,
        eventPublisher = {
            userViewModel.setEvent(it)
        },
    )
}

@Composable
fun RegisterScreen(
    data: UserState,
    onItemClick: () -> Unit,
    eventPublisher: (UserState.Events) -> Unit,
) {
    if (data.SuccesRegister || data.exist) {
        onItemClick()
        return;
    }
    val focusManager = LocalFocusManager.current
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .clickable { focusManager.clearFocus() },
        content = { paddingValues ->
            if (!data.fatching) {
                var fullName by remember { mutableStateOf("") }
                var nickname by remember { mutableStateOf("") }
                var email by remember { mutableStateOf("") }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if(data.error != null && data.error is UserState.Error.PersonExist) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "User already exist",
                                color = Color.Red,
                                fontSize = 24.sp
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(onClick = { onItemClick() }) {
                                Text("Go to home")
                            }
                        }
                        return@Scaffold
                    }
                    if (data.error != null && data.error is UserState.Error.MissingParts) {
                        Row(
                            modifier = Modifier.fillMaxWidth(0.8f),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "All fields are mandatory",
                                color = Color.Red,
                                fontSize = 16.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    OutlinedTextField(
                        value = fullName,
                        onValueChange = { fullName = it },
                        label = { Text("Full name") },
                        singleLine = true,
                        isError = data.error != null && data.error is UserState.Error.BadFullName,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    if (data.error != null && data.error is UserState.Error.BadFullName) {
                        Row(
                            modifier = Modifier.fillMaxWidth(0.8f),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Please enter your name in the format 'Name Surname'",
                                color = Color.Red,
                                fontSize = 16.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    OutlinedTextField(
                        value = nickname,
                        onValueChange = { nickname = it },
                        label = { Text("Nickname") },
                        singleLine = true,
                        isError = data.error != null && data.error is UserState.Error.BadNickname,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    if (data.error != null && data.error is UserState.Error.BadNickname) {
                        Row(
                            modifier = Modifier.fillMaxWidth(0.8f),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "You can only use letters, numbers and underscore '_'",
                                color = Color.Red,
                                fontSize = 16.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") },
                        singleLine = true,
                        isError = data.error != null && data.error is UserState.Error.BadEmail,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    if (data.error != null && data.error is UserState.Error.BadEmail) {
                        Row(
                            modifier = Modifier.fillMaxWidth(0.8f),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Please enter your email in the format email@email.com",
                                color = Color.Red,
                                fontSize = 16.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    buttonFun(
                        eventPublisher = eventPublisher,
                        fullName = fullName,
                        nickname = nickname,
                        email = email

                    )

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
                        Text(text = "Loading...", fontSize = 24.sp)
                        Spacer(modifier = Modifier.height(16.dp))
                        CircularProgressIndicator()
                    }
                }
            }
        }
    )
}
@Composable
fun buttonFun(
    eventPublisher: (UserState.Events) -> Unit,
    fullName: String,
    nickname: String,
    email: String,
) {
    Button(
        onClick = {
            eventPublisher(UserState.Events.Register(fullName, nickname, email))
        }
    ) {
        Text("Register")
    }
}