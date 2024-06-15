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
import org.xml.sax.ErrorHandler

fun NavGraphBuilder.registerScreen(
    route: String,
    onItemClick: () -> Unit,
) = composable(
    route = route,
) { navBackStackEntry ->

    val userViewModel: RegisterViewModel = hiltViewModel(navBackStackEntry)
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
    data: RegisterState,
    onItemClick: () -> Unit,
    eventPublisher: (RegisterState.Events) -> Unit,
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

            if(data.error != null && data.error is RegisterState.Error.LoadingFailed)
                ErrorHandler(data.error.message)
            else if (!data.fatching) {
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
                    if(data.error != null && data.error is RegisterState.Error.PersonExist)
                        ErrorHandler(data.error.message)
                    if (data.error != null && data.error is RegisterState.Error.MissingParts)
                        ErrorHandler(data.error.message)
                    OutlinedTextField(
                        value = fullName,
                        onValueChange = { fullName = it },
                        label = { Text("Full name") },
                        singleLine = true,
                        isError = data.error != null && data.error is RegisterState.Error.BadFullName,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    if (data.error != null && data.error is RegisterState.Error.BadFullName)
                        ErrorHandler(data.error.message)

                    OutlinedTextField(
                        value = nickname,
                        onValueChange = { nickname = it },
                        label = { Text("Nickname") },
                        singleLine = true,
                        isError = data.error != null &&
                                (data.error is RegisterState.Error.BadNickname || data.error is RegisterState.Error.PersonExist),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    if (data.error != null && data.error is RegisterState.Error.BadNickname)
                        ErrorHandler(data.error.message)

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") },
                        singleLine = true,
                        isError = data.error != null && data.error is RegisterState.Error.BadEmail,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    if (data.error != null && data.error is RegisterState.Error.BadEmail)
                        ErrorHandler(data.error.message)
                    Button(
                        onClick = {
                            eventPublisher(RegisterState.Events.Register(fullName, nickname, email))
                        }
                    ) {
                        Text("Register")
                    }

                }
            } else {
                LoadingEditProfile()
            }
        }
    )
}

@Composable
fun ErrorHandler(
    message: String
){
    Row(
        modifier = Modifier.fillMaxWidth(0.8f),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = message,
            color = Color.Red,
            fontSize = 16.sp
        )
    }
    Spacer(modifier = Modifier.height(16.dp))

}



@Composable
fun LoadingEditProfile() {
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

