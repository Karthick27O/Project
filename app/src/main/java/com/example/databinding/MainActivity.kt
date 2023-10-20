package com.example.databinding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.databinding.databinding.ActivityMainBinding
import org.koin.android.ext.android.inject
import java.util.regex.Pattern
import com.example.databinding.PrefHelper
import com.example.databinding.koin.Component


class MainActivity : AppCompatActivity() {
//    val prefHelper: PrefHelper by inject()
    private lateinit var binding: ActivityMainBinding
    private val component = Component()
//    lateinit var prefHelper: PrefHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
//        prefHelper = PrefHelper(this)
        binding.buttonLogin.setOnClickListener {
            val name = binding.editUserName.text.toString()
            val password =binding.editpassword.text.toString()
            if (name.isEmpty()||!validateName(name))
            {
                binding.editUserLayout
                    .error = "Invalid name. Name must be at least 2 characters long and contain only letters and spaces."
            }
            if(password.isEmpty()||!validatePassword(password))
            {
                binding.editPasswordLayout.error="Invalid Password. At least one lowercase,one uppercase letter,one digit,one special character (either @, \$, !, %, *, ?, or &)and  password must be at least 8 characters long"
            }
            else
            {
                saveSession( name, password )
                showMessage( " login" )
                moveIntent()
            }
        }
        binding.buttonRegister.setOnClickListener {
            startActivity(Intent(this,Third_Activity::class.java))
        }

    }


    override fun onStart() {
        super.onStart()
        if (component.prefHelper.getBoolean( Constant.PREF_IS_LOGIN )) {
            moveIntent()
        }
    }

    private fun saveSession(username: String, password: String){
        component.prefHelper.put( Constant.PREF_USERNAME, username )
        component.prefHelper.put( Constant.PREF_PASSWORD, password )
        component.prefHelper.put( Constant.PREF_IS_LOGIN, true)
    }

    private fun moveIntent(){
        startActivity(Intent(this, SecondActivity::class.java))
        finish()
    }

    private fun showMessage(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }
    private fun validateName(name: String): Boolean {
        val namePattern = Pattern.compile("^[a-zA-Z ]{2,}\$")
        val matcher = namePattern.matcher(name)
        return matcher.matches()
    }
    private fun validatePassword(password: String): Boolean {
        // Regular expression for a valid password (at least 8 characters, one uppercase, one lowercase, one digit, one special character)
        val passwordPattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}\$")
        val matcher = passwordPattern.matcher(password)
        return matcher.matches()
    }
}
