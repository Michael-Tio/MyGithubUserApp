package com.michael.mygithubuserapp.api

import com.michael.mygithubuserapp.GithubResponse
import com.michael.mygithubuserapp.ItemsItem
import com.michael.mygithubuserapp.ui.detail.DetailUserResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    fun getUsers(
        @Query("q") query: String
    ): Call<GithubResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Call<List<ItemsItem>>
}