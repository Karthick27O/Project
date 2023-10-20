package com.example.databinding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.databinding.databinding.ActivityThirdBinding
import com.example.databinding.koin.Component
import org.koin.android.ext.android.inject
import org.koin.core.component.inject
import java.util.regex.Pattern
import com.example.databinding.PrefHelper


class Third_Activity : AppCompatActivity() {
//    val prefHelper: PrefHelper by inject()
    private lateinit var binding: ActivityThirdBinding
//    private lateinit var prefHelper: PrefHelper
    private val component = Component()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_third)
//        prefHelper = PrefHelper(this)

        binding.buttonRegister.setOnClickListener {
            val name = binding.editName.text.toString()
            val number = binding.editNumber.text.toString()
            val email = binding.editEmail.text.toString()

            if (name.isEmpty()||!validateName(name)) {
                binding.editName.error = "Invalid name. Name must be at least 2 characters long and contain only letters and spaces."
            }
            if (email.isEmpty()||!validateEmail(email)) {
                binding.editEmail.error = "Invalid email address"
            }
            if (number.isEmpty()||!validateNumber(number)) {
                binding.editNumber.error = "Invalid phone number"
            }
            else
            {
                saveRegistrationData(name, number, email)
                showMessage("Registration successful")
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }


        }
    }

    private fun saveRegistrationData(userName: String, number: String, email: String) {

        component.prefHelper.put(Constant.PREF_USERNAME, userName)
        component.prefHelper.put(Constant.PREF_PHONE, number)
        component.prefHelper.put(Constant.PREF_EMAIL, email)

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


}
//            if (validateName(name)&&validateNumber(number)&&validateEmail(email)) {
//                saveRegistrationData(name, number, email)
//                showMessage("Registration successful")
//                startActivity(Intent(this, MainActivity::class.java))
//                finish()
//            } else {
//                showMessage("Invalid input. Please check your input fields.")
//            }
