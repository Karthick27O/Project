package com.example.databinding

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.room.Room
import com.example.databinding.databinding.ActivityMainBinding
import java.util.regex.Pattern
import com.example.databinding.koin.Component
import com.example.databinding.room_db.AppDataBase
import com.example.databinding.room_db.UserData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.bind

class MainActivity : AppCompatActivity() {

    private lateinit var appDb: AppDataBase
    private lateinit var binding: ActivityMainBinding
    private val component = Component()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appDb = AppDataBase.getDatabase(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        if (component.prefHelper.getBoolean(Constant.PREF_IS_LOGIN)) {
            moveIntent()
        }

        binding.buttonLogin.setOnClickListener {
            validateUserData()
        }

        binding.buttonRegister.setOnClickListener {
            startActivity(Intent(this, Third_Activity::class.java))
        }
    }

    private fun validateUserData() {
        val userName = binding.editUserName.text.toString()
        val password = binding.editpassword.text.toString()

        val userDao = appDb.userDao()

        if (userName.isNotEmpty() && password.isNotEmpty()) {
            GlobalScope.launch(Dispatchers.IO) {
                val userData = userDao.getUserByUsernameAndPassword(userName, password)

                withContext(Dispatchers.Main) {
                    if (userData != null) {
                        showMessage("Login Successful")
                        component.prefHelper.put(Constant.PREF_IS_LOGIN, true)
                        val intent = Intent(this@MainActivity, SecondActivity::class.java).apply {
                            // Pass the retrieved user data as extras to SecondActivity
                            putExtra("USERNAME", userData.userName)
                            putExtra("EMAIL", userData.email)
                            putExtra("NUMBER", userData.number)
                        }
                        startActivity(intent)
                        finish()

                    } else {
                        showMessage("Invalid Login")
                    }
                }
            }
        } else {
            showMessage("Invalid Login")
        }
    }

    private fun moveIntent() {
        startActivity(Intent(this, SecondActivity::class.java))
        finish()
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
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
//            startActivity(Intent(this,Third_Activity::class.java))
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
