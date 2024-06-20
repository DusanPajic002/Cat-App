package com.example.mobilne2.userPage.editProfile

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerHoverIcon
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
import kotlinx.coroutines.delay
import org.xml.sax.ErrorHandler

fun NavGraphBuilder.editProfile(
    route: String,
    onClose: () -> Unit,
) = composable(
    route = route,
) { navBackStackEntry ->

    val editProfileViewModel: EditProfileViewModel = hiltViewModel(navBackStackEntry)
    val state = editProfileViewModel.state.collectAsState()

    EditProfile(
        data = state.value,
        onClose = onClose,
        eventPublisher = {
            editProfileViewModel.setEvent(it)
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EditProfile(
    data: EditProfileState,
    onClose: () -> Unit,
    eventPublisher: (EditProfileState.Events) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    Scaffold(
        modifier = Modifier
        .clickable { focusManager.clearFocus() },
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Edit Profile", color = Color.White) },
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
            Image(
                painter = rememberImagePainter(data = R.drawable.image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            if (data.error != null && data.error is EditProfileState.Error.LoadingFailed) {
                ErrorScreen(
                    message = data.error.message,
                    onClose = onClose,
                    paddingValues = paddingValues
                )
                return@Scaffold
            }

            if (!data.loading) {
                var firstName by remember { mutableStateOf(data.firstName) }
                var lastName by remember { mutableStateOf(data.lastName) }
                var email by remember { mutableStateOf(data.email) }
                var nickname by remember { mutableStateOf(data.nickname) }

                if (data.reset) {
                    firstName = data.firstName
                    lastName = data.lastName
                    email = data.email
                    nickname = data.nickname
                    eventPublisher(EditProfileState.Events.Reset(false))
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    if (data.error != null) {
                        when (data.error) {
                            is EditProfileState.Error.BadFirstName -> ErrorHandler(data.error.message)
                            is EditProfileState.Error.BadLastName -> ErrorHandler(data.error.message)
                            is EditProfileState.Error.BadNickname -> ErrorHandler(data.error.message)
                            is EditProfileState.Error.BadEmail -> ErrorHandler(data.error.message)
                            is EditProfileState.Error.PersonExist -> ErrorHandler(data.error.message)
                            else -> {}
                        }
                    }
                    ProfileRow(
                        label = "First Name",
                        value = firstName,
                        onValueChange = { firstName = it },
                        callEvent = { eventPublisher(EditProfileState.Events.EditFirstName(firstName)) },
                        eventPublisher = eventPublisher,
                    )
                    ProfileRow(
                        label = "Last Name",
                        value = lastName,
                        onValueChange = { lastName = it },
                        callEvent = { eventPublisher(EditProfileState.Events.EditLastName(lastName)) },
                        eventPublisher = eventPublisher,
                    )
                    ProfileRow(
                        label = "Email",
                        value    = email,
                        onValueChange = { email = it },
                        callEvent = { eventPublisher(EditProfileState.Events.EditEmail(email)) },
                        eventPublisher = eventPublisher,
                    )
                    ProfileRow(
                        label = "Nickname",
                        value = nickname,
                        onValueChange = { nickname = it },
                        callEvent = { eventPublisher(EditProfileState.Events.EditNickname(nickname)) },
                        eventPublisher = eventPublisher,
                    )
                }
            } else {
                LoadingEditProfile()
            }
        }
    )

}

@Composable
fun ProfileRow(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    callEvent: () -> Unit,
    eventPublisher: (EditProfileState.Events) -> Unit,
) {
    var edit by remember { mutableStateOf(false) }
    Spacer(modifier = Modifier.height(32.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth().background(Color.White, shape = RoundedCornerShape(8.dp))
            .border(2.dp, Color.Black, shape = RoundedCornerShape(8.dp)).padding(horizontal = 16.dp, vertical = 8.dp),

    ) {

        Crossfade(targetState = edit) { isEditing ->
            if (isEditing) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    TextField(
                        value = value,
                        onValueChange = onValueChange,
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp),
                        label = { Text(label) },
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color(0xFFEAEAEA),
                            focusedContainerColor = Color(0xFFEAEAEA),
                            focusedIndicatorColor = Color.Black,
                            unfocusedPlaceholderColor = Color.Gray,
                            focusedPlaceholderColor = Color.Gray,
                            cursorColor = Color.Black,
                        ),
                    )
                    IconButton(
                        onClick = {
                            edit = false
                            callEvent()
                        },
                        modifier = Modifier
                            .background(Color(0xFFB0FF9B), shape = CircleShape)
                            .padding(end = 6.dp, start = 6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Save"
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(
                        onClick = {
                            edit = false
                            eventPublisher(EditProfileState.Events.Reset(true))
                        },
                        modifier = Modifier
                            .background(Color(0xFFFF8B8B), shape = CircleShape)
                            .padding(end = 6.dp, start = 6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Cancel"
                        )
                    }

                }
            } else {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = value,
                        modifier = Modifier.weight(1f),
                        fontSize = 16.sp
                    )
                    IconButton(
                        onClick = { edit = true },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit"
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ErrorHandler(
    message: String,
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.Red.copy(alpha = 0.1f), shape = RoundedCornerShape(8.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = message,
            color = Color.Red,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
    }
}

@Composable
private fun ErrorScreen(
    message: String,
    onClose: () -> Unit,
    paddingValues: androidx.compose.foundation.layout.PaddingValues
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = message,
            fontSize = 36.sp,
            modifier = Modifier
                .padding(16.dp)
                .padding(top = 64.dp, bottom = 32.dp)
        )
        Button(onClick = { onClose() }) {
            Text("Back")
        }
    }
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
            Text(text = "Loading edit profile...", fontSize = 24.sp)
            Spacer(modifier = Modifier.height(16.dp))
            CircularProgressIndicator()
        }
    }
}