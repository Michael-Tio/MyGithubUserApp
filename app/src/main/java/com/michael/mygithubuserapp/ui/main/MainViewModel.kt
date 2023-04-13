package com.michael.mygithubuserapp.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.michael.mygithubuserapp.api.ApiConfig
import com.michael.mygithubuserapp.GithubResponse
import com.michael.mygithubuserapp.ItemsItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {


    private val _user = MutableLiveData<List<ItemsItem>>()
    val user: LiveData<List<ItemsItem>> = _user
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init{
        getUsers("Alif")
    }

    fun getUsers(pUsername:String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUsers(pUsername)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _user.value = response.body()?.items
                } else {
                    Log.e("MainActivity", "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e("MainActivity", "onFailure: ${t.message}")
            }
        })
    }
}