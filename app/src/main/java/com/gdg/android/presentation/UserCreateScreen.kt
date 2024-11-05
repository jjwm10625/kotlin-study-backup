package com.gdg.android.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.gdg.android.room.UserDatabase
import com.gdg.android.room.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun UserCreateScreen(navController: NavController) {
    val name = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val context = LocalContext.current
    val roomDB = UserDatabase.getDatabase(context)
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(40.dp)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            text = "유저 등록하기",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Icon(
            modifier = Modifier
                .fillMaxWidth()
                .size(150.dp),
            imageVector = Icons.Default.Person,
            contentDescription = null,
            tint = Color.Gray
        )

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 15.dp),
            value = name.value,
            onValueChange = { name.value = it },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFC4C4C4),
                unfocusedContainerColor = Color(0xFFE0E0E0),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = Color.Black
            ),
            placeholder = {
                Text(
                    text = "이름을 입력해주세요",
                    color = Color.Black
                )
            }
        )
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp),
            value = email.value,
            onValueChange = { email.value = it },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFC4C4C4),
                unfocusedContainerColor = Color(0xFFE0E0E0),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = Color.Black
            ),
            placeholder = {
                Text(
                    text = "이메일을 입력해주세요",
                    color = Color.Black
                )
            }
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(
            modifier = Modifier
                .padding(top = 20.dp)
                .width(320.dp)
                .align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF313131),
                contentColor = Color.White
            ),
            onClick = {
                coroutineScope.launch {
                    if (name.value.isNotEmpty() && email.value.isNotEmpty()) {
                        withContext(Dispatchers.IO) {
                            val newUser = UserEntity(name = name.value, email = email.value)
                            roomDB.userDao().insert(newUser) // 새로운 유저 데이터 저장
                        }
                    }
                }
                navController.popBackStack() // 데이터 저장 후 이전 화면으로 되돌아가기
            }
        ) {
            Text(
                text = "등록하기",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                style = TextStyle(
                    letterSpacing = 5.sp
                )
            )
        }
    }
}

@Preview
@Composable
fun UserCreateScreenPreview() {
    UserCreateScreen(navController = rememberNavController())
}