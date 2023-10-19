package com.example.databinding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.databinding.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    lateinit var prefHelper: PrefHelper
    lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_second)

        prefHelper = PrefHelper(this)

        binding.textUsername.text = prefHelper.getString( Constant.PREF_USERNAME )
        binding.textEmail.text=prefHelper.getString( Constant.PREF_EMAIL)
        binding.textPhoneNumber.text=prefHelper.getString( Constant.PREF_PHONE)

        binding.buttonLogout.setOnClickListener {
            prefHelper.clear()
            showMessage( "Clear" )
            moveIntent()
        }
    }

    private fun moveIntent(){
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun showMessage(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }
}