package com.gdg.android.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import coil.compose.AsyncImage
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch

val hobbies = listOf(
    "🍿 혼자 영화 보기", "🛹 스케이트보드 타기", "🎨 그림 그리기", "🎮 게임하기", "📚 독서하기",
    "💥 만화 보기", "👩‍🍳 요리 하기", "🥁 드럼 연주하기", "🎧 음악 감상하기", "📺 애니 정주행하기"
)



@Composable
fun ProfileScreen(navController: NavController, name: String = "조영서", major: String = "소프트웨어학부, 2학년") {

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        AsyncImage(
            modifier = Modifier
                .padding(top = 30.dp)
                .clip(CircleShape)
                .size(130.dp),
            model = "https://avatars.githubusercontent.com/u/152948170?s=400&u=e554cfdf67c75f47e6ee8ab2680afcbe78fb4292&v=4",
            contentDescription = null
        )

        Text(
            text = major,
            modifier = Modifier.padding(top = 20.dp),
            fontSize = 14.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )

        Text(
            text = name,
            modifier = Modifier.padding(top = 5.dp),
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        // Row로 버튼을 감싸 좌우 배치
        Row(
            modifier = Modifier.padding(top = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp) // 버튼 간 간격 설정
        ) {

        Button(
            onClick = { navController.navigate("user") }, // 유저 목록 화면으로 이동
            modifier = Modifier.padding(top = 10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF313131),
                contentColor = Color.White
            )
        ) {
            Text(
                text = "유저 목록",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            )
        }

        Button(
            onClick = {
                (context as? MainActivity)?.lifecycleScope?.launch {
                    (context as? MainActivity)?.saveAutoLoginState(context, false)
                }
                navController.navigate("login") {
                    popUpTo(navController.graph.startDestinationId) { inclusive = true }
                }
            },
            modifier = Modifier.padding(top = 10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF313131),
                contentColor = Color.White
            )
        ) {
            Text(
                text = "로그아웃",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            )
        }
            }


        Text(
            text = "나의 취미",
            modifier = Modifier
                .padding(top = 25.dp)
                .fillMaxWidth(),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 10.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(hobbies) { hobby ->
                Text(
                    modifier = Modifier.padding(vertical = 16.dp),
                    fontSize = 14.sp,
                    text = hobby
                )
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 1.dp,
                    color = Color.LightGray
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(navController = rememberNavController())
}
