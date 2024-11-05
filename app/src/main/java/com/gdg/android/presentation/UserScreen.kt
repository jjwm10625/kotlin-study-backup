package com.gdg.android.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.gdg.android.api.User
import com.gdg.android.room.UserDatabase
import com.gdg.android.room.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserScreen(navController: NavController) {
    val mainViewModel: MainViewModel = viewModel()
    val users by mainViewModel.users.observeAsState(emptyList())

    val context = LocalContext.current
    val roomDB = UserDatabase.getDatabase(context)
    val coroutineScope = rememberCoroutineScope()
    val userList = remember { mutableStateListOf<UserEntity>() }

    // Load data asynchronously
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            val users = withContext(Dispatchers.IO) {
                roomDB.userDao().selectAll() // Fetch all user data (background)
            }
            userList.clear()
            userList.addAll(users)
        }
    }
    LaunchedEffect(Unit) {
        mainViewModel.getUsers()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                title = {
                    Text(
                        text = "User List",
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        floatingActionButton = {
            SmallFloatingActionButton(
                shape = CircleShape,
                containerColor = Color.Gray,
                contentColor = Color.White,
                onClick = { navController.navigate("userCreate") } // Navigate to user create screen
            ) {
                Icon(
                    modifier = Modifier.padding(15.dp),
                    imageVector = Icons.Filled.Edit,
                    contentDescription = null
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(innerPadding)
        ) {
            LazyColumn {
                items(users) { user ->
                    UserItem(user)
                }
                itemsIndexed(userList) { _, user ->
                    UserCreateItem(
                        user = user,
                        onDeleteClick = {
                            coroutineScope.launch {
                                withContext(Dispatchers.IO) {
                                    roomDB.userDao().delete(user) // Delete user
                                }
                                userList.remove(user) // Remove user from UI
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun UserItem(user: User) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 18.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = user.firstName,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = user.email,
                color = Color.Gray
            )
        }
        AsyncImage(
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(12.dp)),
            model = user.avatar,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun UserCreateItem(
    user: UserEntity,
    onDeleteClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 18.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(text = user.name, color = Color.Black)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = user.email, color = Color.Gray)
        }
        Icon(
            modifier = Modifier.clickable { onDeleteClick() }, // Delete click event
            imageVector = Icons.Filled.Delete,
            contentDescription = null,
        )
    }
    Divider(
        modifier = Modifier.fillMaxWidth(),
        thickness = 1.dp,
        color = Color.LightGray
    )
}