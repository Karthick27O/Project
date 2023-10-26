package com.example.databinding.retrofit

import com.example.databinding.room_db.UserData
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserApi {

    @GET("/api/users/{uid}")
    fun getUser(@Path("uid") uid: String?): Call<UserApiData?>?

}