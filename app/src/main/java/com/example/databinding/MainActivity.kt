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

                showMessage("Login Successful")
                prefHelper.put(Constant.PREF_IS_LOGIN, true)
                prefHelper.put(Constant.PREF_USERNAME, userData.userName.orEmpty())
                val intent = Intent(this@MainActivity,ActivityFragment::class.java).apply {
                    putExtra("USERNAME", userData.userName)
                    putExtra("EMAIL", userData.email)
                    putExtra("NUMBER", userData.number)
                }
                startActivity(intent)
                finish()
            }
        } else {
            showMessage("Invalid Login")
        }
    }

    private fun moveIntent() {
        startActivity(Intent(this, ActivityFragment::class.java))
        finish()
    }

    private fun showMessage(message: String) {
        val actualMessage = "Clear"
        if (message == actualMessage) {
            Toast.makeText(applicationContext, actualMessage, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
        }
    }

//    private fun showMessage(message: String) {
//        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
//    }

//    private fun saveSession(username: String) {
//        val intent = Intent(this@MainActivity, SecondActivity::class.java).apply {
//            putExtra("USERNAME", username)
//
//        }
}


//sharedpref method
//        prefHelper = PrefHelper(this)
//    val prefHelper: PrefHelper by inject()
//    lateinit var prefHelper: PrefHelper
//koin shared pref
//    binding.buttonLogin.setOnClickListener {
//            val name = binding.editUserName.text.toString()
//            val password =binding.editpassword.text.toString()
//            if (name.isEmpty()||!validateName(name))
//            {
//                binding.editUserLayout
//                    .error = "Invalid name. Name must be at least 2 characters long and contain only letters and spaces."
//            }
//            if(password.isEmpty()||!validatePassword(password))
//            {
//                binding.editPasswordLayout.error="Invalid Password. At least one lowercase,one uppercase letter,one digit,one special character (either @, \$, !, %, *, ?, or &)and  password must be at least 8 characters long"
//            }
//            else
//            {
//                saveSession( name, password )
//                showMessage( " login" )
//                moveIntent()
//            }
//        }
//        binding.buttonRegister.setOnClickListener {
//            startActivity(Intent(this,ThirdActivity::class.java))
//        }
//
//    }
//
//
//    override fun onStart() {
//        super.onStart()
//        if (component.prefHelper.getBoolean( Constant.PREF_IS_LOGIN )) {
//            moveIntent()
//        }
//    }
//
//    private fun saveSession(username: String, password: String){
//        component.prefHelper.put( Constant.PREF_USERNAME, username )
//        component.prefHelper.put( Constant.PREF_PASSWORD, password )
//        component.prefHelper.put( Constant.PREF_IS_LOGIN, true)
//    }
