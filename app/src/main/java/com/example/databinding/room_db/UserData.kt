package com.example.databinding.room_db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userData_table")
data class UserData(
    @PrimaryKey(autoGenerate = true) val id:Int?,
    @ColumnInfo(name = "User_Name") val userName:String?,
    @ColumnInfo(name = "Email") val email:String?,
    @ColumnInfo(name = "Number") val number: String,
    @ColumnInfo(name = "Password") val password:String?


)
