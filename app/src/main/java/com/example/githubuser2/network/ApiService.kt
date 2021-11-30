package com.example.githubuser2.network


import com.example.githubuser2.data.FollowResponse
import com.example.githubuser2.data.SearchResponse
import com.example.githubuser2.data.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getListUsers(
        @Query("q") q: String?
    ): Call<SearchResponse>

    @GET("users/{login}")
    @Headers("Authorization: token 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")
    fun getDetailUsers(
        @Path("login") id: String?
    ): Call<UserResponse>

    @GET("users/{login}/followers")
    fun getFollowersUsers(
        @Path("login") id: String?
    ): Call<FollowResponse>

    @GET("users/{login}/following")
    fun getFollowingUsers(
        @Path("login") id: String?
    ): Call<FollowResponse>
}