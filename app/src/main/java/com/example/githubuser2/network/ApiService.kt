package com.example.githubuser2.network

import com.example.githubuser2.BuildConfig
import com.example.githubuser2.data.FollowResponseItem
import com.example.githubuser2.data.SearchResponse
import com.example.githubuser2.data.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("search/users")
    @Headers("Authorization: ${BuildConfig.GITHUB_TOKEN}")
    fun getListUsers(
        @Query("q") q: String?
    ): Call<SearchResponse>

    @GET("users/{login}")
    @Headers("Authorization: ${BuildConfig.GITHUB_TOKEN}")
    fun getDetailUsers(
        @Path("login") id: String?
    ): Call<UserResponse>

    @GET("users/{login}/followers")
    @Headers("Authorization: ${BuildConfig.GITHUB_TOKEN}")
    fun getFollowersUsers(
        @Path("login") id: String?
    ): Call<List<FollowResponseItem>>

    @GET("users/{login}/following")
    @Headers("Authorization: ${BuildConfig.GITHUB_TOKEN}")
    fun getFollowingUsers(
        @Path("login") id: String?
    ): Call<List<FollowResponseItem>>
}

