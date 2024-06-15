package com.example.mobilne2.userPage.editProfile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

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

@Composable
private fun EditProfile(
    data: EditProfileState,
    onClose: () -> Unit,
    eventPublisher: (EditProfileState.Events) -> Unit,
) {
    if (data.error != null) {
        //return
    }else if (data.fatching) {
        //return
    }else {
        LoadingEditProfile()
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