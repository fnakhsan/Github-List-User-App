package com.example.githubuser3.data.network

import com.example.githubuser3.BuildConfig
import com.example.githubuser3.data.model.UserModel
import com.example.githubuser3.data.model.SearchModel
import com.example.githubuser3.data.model.FollowModel
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: ${BuildConfig.GITHUB_TOKEN}")
    fun getListUsers(
        @Query("q") q: String?
    ): Call<SearchModel>

    @GET("users/{login}")
    @Headers("Authorization: ${BuildConfig.GITHUB_TOKEN}")
    fun getDetailUsers(
        @Path("login") id: String?
    ): Call<UserModel>

    @GET("users/{login}/followers")
    @Headers("Authorization: ${BuildConfig.GITHUB_TOKEN}")
    fun getFollowersUsers(
        @Path("login") id: String?
    ): Call<List<FollowModel>>

    @GET("users/{login}/following")
    @Headers("Authorization: ${BuildConfig.GITHUB_TOKEN}")
    fun getFollowingUsers(
        @Path("login") id: String?
    ): Call<List<FollowModel>>
}