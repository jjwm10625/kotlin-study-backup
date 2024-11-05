package com.gdg.android.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user") // 테이블명을 user로 설정
data class UserEntity(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "email") val email: String,
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int = 0
)