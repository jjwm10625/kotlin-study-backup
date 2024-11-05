// LoginPage.kt
package com.gdg.android.presentation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@Composable
fun LoginPage(navController: NavController? = null) {
    val context = LocalContext.current

    val majorTextValue = remember { mutableStateOf("") }
    val nameTextValue = remember { mutableStateOf("") }
    var majorError by remember { mutableStateOf("") }
    var nameError by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(30.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "안녕하세요, 여러분",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )

        Spacer(modifier = Modifier.height(20.dp))

        // 학부 입력
        TextFieldSection(label = "학부", textValue = majorTextValue, errorText = majorError)

        Spacer(modifier = Modifier.height(16.dp))

        // 이름 입력
        TextFieldSection(label = "이름", textValue = nameTextValue, errorText = nameError)

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = {
                majorError = if (majorTextValue.value.isEmpty()) "학부를 입력해주세요." else ""
                nameError = if (nameTextValue.value.isEmpty()) "이름을 입력해주세요." else ""

                (context as? MainActivity)?.lifecycleScope?.launch {
                    (context as? MainActivity)?.saveAutoLoginState(context, true)
                }

                if (majorTextValue.value.isNotEmpty() && nameTextValue.value.isNotEmpty()) {
                    Toast.makeText(context, "로그인에 성공했습니다", Toast.LENGTH_SHORT).show()
                    navController?.navigate("profile") {
                        popUpTo(navController.graph.startDestinationId) { inclusive = true }

                    }
                }
            },
            modifier = Modifier
                .padding(top = 20.dp)
                .width(320.dp)
                .align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF313131),
                contentColor = Color.White
            )
        ) {
            Text(
                text = "LOGIN",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                style = TextStyle(
                    letterSpacing = 5.sp
                )
            )
        }
    }
}

@Composable
fun TextFieldSection(label: String, textValue: MutableState<String>, errorText: String) {
    // label에 따라 올바른 조사를 선택
    val placeholderText = if (label.endsWith("이름")) "${label}을 입력해주세요" else "${label}를 입력해주세요"

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontSize = 16.sp,
            color = Color(0xFF1C1C1C),
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth()
        )

        TextField(
            value = textValue.value,
            onValueChange = { textValue.value = it },
            label = { Text(placeholderText, color = Color.Black) }, // 변경된 placeholderText 사용
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
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth()
        )

        if (errorText.isNotEmpty()) {
            Text(
                text = errorText,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun LoginPagePreview() {
    LoginPage(navController = null)
}