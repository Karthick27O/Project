package com.example.databinding.room_db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userData_table")
data class UserData(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo("User_Name") val userName: String?,
    @ColumnInfo("Email") val email: String?,
    @ColumnInfo("Number") val number: String,
    @ColumnInfo("Password") val password: String?,
    )
