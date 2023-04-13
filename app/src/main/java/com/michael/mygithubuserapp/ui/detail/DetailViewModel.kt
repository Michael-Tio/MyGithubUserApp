package com.michael.mygithubuserapp.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.michael.mygithubuserapp.api.ApiConfig
import com.michael.mygithubuserapp.ItemsItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel: ViewModel() {
    private val _user = MutableLiveData<DetailUserResponse>()
    val user: LiveData<DetailUserResponse> = _user

    private val _following = MutableLiveData<List<ItemsItem>>()
    val following: LiveData<List<ItemsItem>> = _following
    private val _follower = MutableLiveData<List<ItemsItem>>()
    val follower: LiveData<List<ItemsItem>> = _follower

    private val _isLoadingDetail = MutableLiveData<Boolean>()
    val isLoadingDetail: LiveData<Boolean> = _isLoadingDetail
    private val _isLoadingFolls = MutableLiveData<Boolean>()
    val isLoadingFolls: LiveData<Boolean> = _isLoadingFolls


    fun getUserChosen(pUsername:String) {
        _isLoadingDetail.value = true
        val client = ApiConfig.getApiService().getDetailUser(pUsername)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                if (response.isSuccessful) {
                    _user.value = response.body()
                    _isLoadingDetail.value = false
                } else {
                    Log.e("MainActivity", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoadingDetail.value = false
                Log.e("MainActivity", "onFailure: ${t.message}")
            }
        })
    }

    fun listFollowing(pUsername:String) {
        _isLoadingFolls.value = true
        val client = ApiConfig.getApiService().getFollowing(pUsername)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                if (response.isSuccessful) {
                    _following.value = response.body()
                    _isLoadingFolls.value = false
                } else {
                    Log.e("MainActivity", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoadingFolls.value = false
                Log.e("MainActivity", "onFailure: ${t.message}")
            }
        })
    }

    fun listFollower(pUsername:String) {
        _isLoadingFolls.value = true
        val client = ApiConfig.getApiService().getFollowers(pUsername)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                if (response.isSuccessful) {
                    _follower.value = response.body()
                    _isLoadingFolls.value = false
                } else {
                    Log.e("MainActivity", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoadingFolls.value = false
                Log.e("MainActivity", "onFailure: ${t.message}")
            }
        })
    }
}