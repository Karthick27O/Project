package com.example.databinding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.databinding.databinding.ActivityMainBinding
import com.example.databinding.fragment.ActivityFragment
import com.example.databinding.room_db.AppDataBase
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val appDb: AppDataBase by inject()
    private lateinit var binding: ActivityMainBinding
    private val prefHelper: PrefHelper by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        if (prefHelper.getBoolean(Constant.PREF_IS_LOGIN)) {
            moveIntent()
        }

        binding.buttonLogin.setOnClickListener {
            validateUserData()
        }

        binding.buttonRegister.setOnClickListener {
            startActivity(Intent(this, ThirdActivity::class.java))
        }
    }

    private fun validateUserData() {
        val userName = binding.editUserName.text.toString()
        val password = binding.editpassword.text.toString()

        val userDao = appDb.userDao()

        if (userName.isNotEmpty() && password.isNotEmpty()) {
            lifecycleScope.launch {
                val userData = userDao.getUserByUsernameAndPassword(userName, password)
                if (userData == null) {
                    showMessage("User Doesn't exist")
                } else {
                    showMessage("Login Successful")
                    prefHelper.put(Constant.PREF_IS_LOGIN, true)
                    prefHelper.put(Constant.PREF_USERNAME, userData.userName.orEmpty())
                    val intent = Intent(this@MainActivity, ActivityFragment::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        } else {
            showMessage("Invalid username or password")
        }
    }


    private fun moveIntent() {
        startActivity(Intent(this, SecondActivity::class.java))
        finish()
    }

    private fun showMessage(message: String) {
        val actualMessage = "Clear"
        if (message == actualMessage) {
            Toast.makeText(this, actualMessage, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }
}