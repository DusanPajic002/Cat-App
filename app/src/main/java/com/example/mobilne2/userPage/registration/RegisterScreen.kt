package com.example.mobilne2.userPage.registration

import androidx.compose.foundation.Image
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import coil.compose.rememberImagePainter
import com.example.mobilne2.R

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
        return
    }
    val focusManager = LocalFocusManager.current
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .clickable { focusManager.clearFocus() },
        content = { paddingValues ->

            if(data.error != null && data.error is RegisterState.Error.LoadingFailed) {
                ErrorHandler(data.error.message)
            } else if (!data.fatching) {

                var fullName by remember { mutableStateOf("") }
                var nickname by remember { mutableStateOf("") }
                var email by remember { mutableStateOf("") }
                Image(
                    painter = rememberImagePainter(data = R.drawable.image),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
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

                    registrationInput(
                        value = fullName,
                        onValueChange = { fullName = it },
                        label = "Full name",
                        isError = data.error != null && data.error is RegisterState.Error.BadFullName,
                        error = if (data.error is RegisterState.Error.BadFullName) data.error.message else "skip"
                    )
                    registrationInput(
                        value = nickname,
                        onValueChange = { nickname = it },
                        label = "Nickname",
                        isError = data.error != null &&
                                (data.error is RegisterState.Error.BadNickname || data.error is RegisterState.Error.PersonExist),
                        error = if (data.error is RegisterState.Error.BadNickname) data.error.message else "skip"
                    )
                    registrationInput(
                        value = email,
                        onValueChange = { email = it },
                        label = "Email",
                        isError = data.error != null && data.error is RegisterState.Error.BadEmail,
                        error = if (data.error is RegisterState.Error.BadEmail) data.error.message else "skip"
                    )
                    Spacer(modifier = Modifier.height(48.dp))
                    Button(
                        onClick = {
                            eventPublisher(RegisterState.Events.Register(fullName, nickname, email))
                        }
                    ) {
                        Text("Register",
                            fontSize = 20.sp,
                            modifier = Modifier
                                .padding(6.dp))
                    }
                }
            } else {
                LoadingEditProfile()
            }
        }
    )
}

@Composable
fun registrationInput(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isError: Boolean,
    error: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            singleLine = true,
            isError = isError,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color(0xFFEAEAEA),
                focusedContainerColor = Color(0xFFEAEAEA),
                focusedIndicatorColor = Color.Gray,
                unfocusedPlaceholderColor = Color.Gray,
                focusedPlaceholderColor = Color.Gray,
                cursorColor = Color.Black,
            ),
        )
        if (error != "skip") {
            ErrorHandler(message = error )
        }
    }
}


@Composable
fun ErrorHandler(
    message: String
){
    Column(
        modifier = Modifier.fillMaxWidth(0.8f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = message,
            color = Color.Red,
            fontSize = 16.sp,
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

