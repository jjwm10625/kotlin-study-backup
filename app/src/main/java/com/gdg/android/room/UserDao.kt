package com.gdg.android.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Insert
    fun insert(user: UserEntity)

    @Delete
    fun delete(user: UserEntity)

    @Query("SELECT * FROM user")
    fun selectAll(): List<UserEntity>
}