package com.gdg.android.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gdg.android.api.ServicePool
import com.gdg.android.api.User
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _users = MutableLiveData<List<User>>() // 내부에서 수정 가능한 데이터
    val users: LiveData<List<User>> get() = _users // 외부에서 읽기만 가능한 데이터

    fun getUsers() {
        viewModelScope.launch {
            runCatching { ServicePool.userService.getUsers(page = 2) }
                .onSuccess {
                    _users.value = it.data
                    Log.d("MainViewModel", "getUsers: ${it.data}")
                }
                .onFailure {
                    _users.value = emptyList()
                    Log.e("MainViewModel", "getUsers: ${it.message}")
                }
        }
    }
}