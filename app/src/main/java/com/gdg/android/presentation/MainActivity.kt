package com.gdg.android.presentation

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auto_login")

class MainActivity : ComponentActivity() {

    // 키 생성
    private val AUTO_LOGIN_KEY = booleanPreferencesKey("auto_login")

    // 자동 로그인 상태 저장 함수
    suspend fun saveAutoLoginState(context: Context, isLoggedIn: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[AUTO_LOGIN_KEY] = isLoggedIn
        }
    }

    // 자동 로그인 상태 불러오기 함수
    fun getAutoLoginState(context: Context): Flow<Boolean> {
        return context.dataStore.data
            .map { preferences ->
                preferences[AUTO_LOGIN_KEY] ?: false // 기본값은 false
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        lifecycleScope.launch {
            val isLoggedIn = getAutoLoginState(applicationContext).first() // 자동 로그인 상태 확인

            setContent {
                val navController = rememberNavController()

                // NavHost 설정
                NavHost(
                    navController = navController,
                    startDestination = if (isLoggedIn) "main" else "login" // 자동 로그인 여부에 따라 시작 화면 설정
                ) {
                    composable("login") {
                        LoginPage(navController)
                    }
                    composable("profile") {
                        ProfileScreen(navController)
                    }
                    composable("user") {
                        UserScreen(navController)
                    }
                    composable("userCreate") {
                        UserCreateScreen(navController)
                    }
                }
            }
        }
    }
}