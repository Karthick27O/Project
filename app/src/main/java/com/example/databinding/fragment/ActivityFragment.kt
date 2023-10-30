package com.example.databinding.fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.databinding.R
import com.example.databinding.databinding.ActivityFragmentBinding

class ActivityFragment : AppCompatActivity() {


    private lateinit var binding: ActivityFragmentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_fragment)

        val firstFragment = Fragment1()
        val secondFragment= Fragment2()

        setCurrentFragment(firstFragment)

        binding.bottomNavigationView.setOnItemSelectedListener{
            when(it.itemId){
                R.id.mihome -> setCurrentFragment(firstFragment)
                R.id.miuser -> setCurrentFragment(secondFragment)
            }
            true
        }
    }
    private fun setCurrentFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,fragment)
            commit()
        }
    }
}