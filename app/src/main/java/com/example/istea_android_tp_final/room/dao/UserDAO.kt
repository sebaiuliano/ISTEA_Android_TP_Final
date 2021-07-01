package com.example.istea_android_tp_final.room.dao

import androidx.room.*
import com.example.istea_android_tp_final.entities.user.User

@Dao
interface UserDAO {
    @Insert
    fun insert(user: User):Long
    @Update
    fun update(user: User)
    @Delete
    fun delete(user: User)
    @Query("SELECT * FROM users WHERE username = :username")
    fun getByUserName(username: String):User?
}