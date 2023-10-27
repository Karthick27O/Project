package com.example.databinding.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.databinding.Constant
import com.example.databinding.MainActivity
import com.example.databinding.PrefHelper
import com.example.databinding.R
import com.example.databinding.databinding.Fragment1Binding
import com.example.databinding.room_db.AppDataBase
import com.example.databinding.room_db.UserData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject

class Fragment1: Fragment(R.layout.fragment1) {

    private val appDb: AppDataBase by inject()
    private val prefHelper: PrefHelper by inject()
    private lateinit var binding: Fragment1Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment1,container,false)

        val userName = prefHelper.getString(Constant.PREF_USERNAME)

        if (userName != null) {
            lateinit var userData: UserData
            lifecycleScope.launch {
                userData = appDb.userDao().findByRoll(userName.toString())
                displayData(userData)
            }
        }

        binding.buttonLogout.setOnClickListener {
            prefHelper.clear()
            moveIntent()
        }
        return binding.root
    }

    private suspend fun displayData(userData: UserData) {

        withContext(Dispatchers.Main) {

            binding.textUsername.text = userData.userName
            binding.textEmail.text = userData.email
            binding.textPhoneNumber.text = userData.number

        }

    }
    private fun moveIntent() {
        startActivity(Intent(activity, MainActivity::class.java))
    }

}