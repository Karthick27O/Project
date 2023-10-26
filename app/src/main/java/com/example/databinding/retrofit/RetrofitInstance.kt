package com.example.databinding.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {

    val api: UserApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://reqres.in"
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserApi::class.java)
    }
}