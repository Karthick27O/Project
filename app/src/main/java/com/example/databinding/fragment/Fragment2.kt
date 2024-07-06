
package com.example.databinding.fragment

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.databinding.R
import com.example.databinding.databinding.Fragment2Binding
import com.example.databinding.retrofit.RetrofitInstance
import com.example.databinding.retrofit.TodoAdapter
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import java.io.IOException

class Fragment2: Fragment(R.layout.fragment2) {
    private val retrofitInstance : RetrofitInstance by inject()
    private lateinit var binding:Fragment2Binding
    private lateinit var todoAdapter: TodoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment2,container,false)
        setupRecyclerView()

        lifecycleScope.launch {
            binding.progressBar.isVisible = true
            val response = try {
                retrofitInstance.api.getTodos()
            } catch(e: IOException) {
                Log.e(ContentValues.TAG, "IOException, you might not have internet connection")
                binding.progressBar.isVisible = false
                return@launch
            } catch (e: retrofit2.HttpException) {
                Log.e(ContentValues.TAG, "HttpException, unexpected response")
                binding.progressBar.isVisible = false

                return@launch
            }
            if(response.isSuccessful && response.body() != null) {
                todoAdapter.todos = response.body()!!
            } else {
                Log.e(ContentValues.TAG, "Response not successful")
            }
            binding.progressBar.isVisible = false
        }
        return binding.root
    }
    private fun setupRecyclerView() = binding.rvRetrofit.apply {
        todoAdapter = TodoAdapter()
        adapter = todoAdapter
        layoutManager = LinearLayoutManager(activity)
    }

    }