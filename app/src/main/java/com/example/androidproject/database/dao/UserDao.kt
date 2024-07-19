package com.example.androidproject.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.androidproject.database.Entities.User


@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)

    @Query("SELECT COUNT(*) FROM user")
    fun getUserCount(): Int

    @Query("SELECT * FROM user")
    fun getAllUser(): User

    @Query("SELECT * FROM user WHERE id = :userId")
    fun getUser(userId: Int): User
}