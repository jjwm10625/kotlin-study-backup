package com.gdg.android

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.gdg.android.ui.theme.GDGAndroidTheme

val hobbies = listOf(
    "🍿 혼자 영화 보기", "🛹 스케이트보드 타기", "🎨 그림 그리기", "🎮 게임하기", "📚 독서하기",
    "💥 만화 보기", "👩‍🍳 요리 하기", "🥁 드럼 연주하기", "🎧 음악 감상하기", "📺 애니 정주행하기"
)

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GDGAndroidTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    ProfileScreen(
                        name = "조영서",
                        major = "소프트웨어학부, 2학년", // 동적으로 전공 및 학년 전달
                    )
                }
            }
        }
    }
}

@Composable
fun ProfileScreen(name: String, major: String, modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        // 프로필 이미지
        AsyncImage(
            modifier = Modifier
                .padding(top = 30.dp)
                .clip(CircleShape)
                .size(130.dp),
            model = "https://avatars.githubusercontent.com/u/152948170?s=400&u=e554cfdf67c75f47e6ee8ab2680afcbe78fb4292&v=4",
            contentDescription = null,
            placeholder = painterResource(id = R.drawable.ic_launcher_background)
        )

        // 전공 텍스트
        Text(
            text = major,
            modifier = modifier.padding(top = 20.dp),
            fontSize = 14.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )

        // 이름 텍스트
        Text(
            text = name,
            modifier = modifier.padding(top = 5.dp),
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        // 취미 제목 텍스트
        Text(
            text = "나의 취미",
            modifier = modifier
                .padding(top = 25.dp)
                .fillMaxWidth(),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start
        )

        // 취미 리스트
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(top = 10.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(hobbies) { hobby ->
                Text(
                    modifier = Modifier.padding(vertical = 16.dp),
                    fontSize = 14.sp,
                    text = hobby
                )
                // 구분선
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
fun ProfilePreview() {
    GDGAndroidTheme {
        ProfileScreen(name = "조영서", major = "소프트웨어학부, 2학년") // 미리보기에도 동적 값 사용
    }
}
