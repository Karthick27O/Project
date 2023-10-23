package com.example.databinding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.databinding.databinding.ActivitySecondBinding
import com.example.databinding.room_db.AppDataBase
import com.example.databinding.room_db.UserData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject


class SecondActivity : AppCompatActivity() {

    private lateinit var appDb: AppDataBase
    private lateinit var binding: ActivitySecondBinding
    private val prefHelper: PrefHelper by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appDb = AppDataBase.getDatabase(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_second)

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
            showMessage("Clear")
            moveIntent()
        }
    }

    private suspend fun displayData(userData: UserData) {

        withContext(Dispatchers.Main) {

            binding.textUsername.text = userData.userName
            binding.textEmail.text = userData.email
            binding.textPhoneNumber.text = userData.number

        }

    }

    private fun moveIntent() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun showMessage(message: String) {
        val actualMessage = "Clear"
        if (message == actualMessage) {
            Toast.makeText(applicationContext, actualMessage, Toast.LENGTH_SHORT).show()
        } else {
            // Handle other cases if needed
            Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
        }
    }

}

//        binding.textUsername.text= userName
//        binding.textEmail.text = email
//        binding.textPhoneNumber.text = number

//        binding.textUsername.text = component.prefHelper.getString( Constant.PREF_USERNAME )
//        binding.textEmail.text=component.prefHelper.getString( Constant.PREF_EMAIL)
//        binding.textPhoneNumber.text=component.prefHelper.getString( Constant.PREF_PHONE)