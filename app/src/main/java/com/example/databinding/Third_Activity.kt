package com.example.databinding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.databinding.databinding.ActivityThirdBinding
import com.example.databinding.koin.Component
import com.example.databinding.room_db.AppDataBase
import java.util.regex.Pattern
import com.example.databinding.room_db.UserData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class Third_Activity : AppCompatActivity() {
//    val prefHelper: PrefHelper by inject()
//    private lateinit var prefHelper: PrefHelper
    private lateinit var binding: ActivityThirdBinding
    private lateinit var appDb : AppDataBase
    private val component = Component()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appDb = AppDataBase.getDatabase(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_third)
//        prefHelper = PrefHelper(this)

        binding.buttonRegister.setOnClickListener {

            saveRegistration()
        }
    }

    private fun saveRegistration(){
        val name = binding.editName.text.toString()
        val number= binding.editNumber.text.toString()
        val email = binding.editEmail.text.toString()
        val password = binding.editPassword.text.toString()

        if (name.isEmpty()||!validateName(name)) {
        binding.editName.error = "Invalid name. Name must be at least 2 characters long and contain only letters and spaces."
        }

        if (email.isEmpty()||!validateEmail(email)) {
        binding.editEmail.error = "Invalid email address"
        }

        if (number.isEmpty()||!validateNumber(number)) {
        binding.editNumber.error = "Invalid phone number"
        }

        if(password.isEmpty())
        {
            binding.editPassword.error="Invalid Password. At least one lowercase,one uppercase letter,one digit,one special character (either @, \$, !, %, *, ?, or &)and  password must be at least 8 characters long"
        }
        else
        {
            val userData = UserData(null,name,email,number,password)

            GlobalScope.launch(Dispatchers.IO) {

                appDb.userDao().insert(userData)
            }

            showMessage("Registration successful")

            startActivity(Intent(this, MainActivity::class.java))
            finish()

        }


    }
    private fun showMessage(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }
    private fun validateName(name: String): Boolean {
        val namePattern = Pattern.compile("^[a-zA-Z ]{2,}\$")
        val matcher = namePattern.matcher(name)
        return matcher.matches()
    }
    private fun validateNumber(number: String): Boolean {
        // You can use a specific regex pattern for phone numbers validation
        val phoneRegex = "^[0-9]{10}\$"
        return number.matches(phoneRegex.toRegex())
    }
    private fun validateEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\$"
        return email.matches(emailRegex.toRegex())
    }
    private fun validatePassword(password: String): Boolean {
        // Regular expression for a valid password (at least 8 characters, one uppercase, one lowercase, one digit, one special character)
        val passwordPattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}\$")
        val matcher = passwordPattern.matcher(password)
        return matcher.matches()
    }


}
//Shared Preference method
//binding.buttonRegister.setOnClickListener {
//    val name = binding.editName.text.toString()
//    val number = binding.editNumber.text.toString()
//    val email = binding.editEmail.text.toString()
//
//    if (name.isEmpty()||!validateName(name)) {
//        binding.editName.error = "Invalid name. Name must be at least 2 characters long and contain only letters and spaces."
//    }
//    if (email.isEmpty()||!validateEmail(email)) {
//        binding.editEmail.error = "Invalid email address"
//    }
//    if (number.isEmpty()||!validateNumber(number)) {
//        binding.editNumber.error = "Invalid phone number"
//    }
//    else
//    {
//        saveRegistrationData(name, number, email)
//        showMessage("Registration successful")
//        startActivity(Intent(this, MainActivity::class.java))
//        finish()
//    }
//    private fun saveRegistrationData(userName: String, number: String, email: String) {
//
//        component.prefHelper.put(Constant.PREF_USERNAME, userName)
//        component.prefHelper.put(Constant.PREF_PHONE, number)
//        component.prefHelper.put(Constant.PREF_EMAIL, email)
//
//    }
//
//
//}
//            if (validateName(name)&&validateNumber(number)&&validateEmail(email)) {
//                saveRegistrationData(name, number, email)
//                showMessage("Registration successful")
//                startActivity(Intent(this, MainActivity::class.java))
//                finish()
//            } else {
//                showMessage("Invalid input. Please check your input fields.")
//            }
