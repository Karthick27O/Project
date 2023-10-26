package com.example.databinding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.databinding.databinding.ActivityApiImplementationBinding
import com.example.databinding.retrofit.RetrofitInstance
import com.example.databinding.retrofit.UserApiData
import org.koin.android.ext.android.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiImplementation : AppCompatActivity() {
    private lateinit var binding: ActivityApiImplementationBinding
    private val retrofitInstance : RetrofitInstance by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_api_implementation)

        val name = binding.textView2
        val email = binding.textView3
        val getBtn = binding.button
        val requestUser = retrofitInstance.api

        getBtn.setOnClickListener {
            val call: Call<UserApiData?>? = requestUser.getUser("3")
            call?.enqueue(object :Callback<UserApiData?>{
                override fun onResponse(
                    call: Call<UserApiData?>,
                    response: Response<UserApiData?>,
                ) {
                    val userApiData:UserApiData? = response.body()
                    name.text=userApiData?.data?.first_name.toString()
                    email.text=userApiData?.data?.email.toString()
                }

                override fun onFailure(call: Call<UserApiData?>, t: Throwable) {
                    name.error="Cannot fetch data"
                    email.error="Cannot fetch data"
                }
            })
        }

    }
}


