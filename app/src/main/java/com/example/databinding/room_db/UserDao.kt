package com.example.databinding.room_db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Query("SELECT * FROM userData_table")
    fun getAll(): List<UserData>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(userData: UserData)

    @Query("SELECT * FROM userData_table WHERE User_Name = :username AND Password = :password")
    suspend fun getUserByUsernameAndPassword(username: String?, password: String?): UserData?

    @Query("SELECT * FROM userData_table WHERE User_Name LIKE :roll LIMIT 1")
    suspend fun findByRoll(roll: String): UserData?

    @Query("DELETE FROM userData_table WHERE User_Name = :username")
    suspend fun deleteUserByUsername(username: String)

    @Query("UPDATE userData_table SET Password = :newPassword WHERE User_Name = :username")
    suspend fun updateUserPassword(username: String, newPassword: String)

    @Query("SELECT COUNT(*) FROM userData_table")
    suspend fun countUsers(): Int

    @Query("SELECT COUNT(*) FROM userData_table WHERE User_Name = :username")
    suspend fun isUserExists(username: String): Int
}
