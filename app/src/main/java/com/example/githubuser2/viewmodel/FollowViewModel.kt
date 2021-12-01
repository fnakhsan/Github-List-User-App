package com.example.githubuser2.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser2.data.FollowResponseItem
import com.example.githubuser2.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel: ViewModel() {
    private val _followerResponse = MutableLiveData<List<FollowResponseItem>>()
    val followerResponse: LiveData<List<FollowResponseItem>> = _followerResponse

    private val _followingResponse = MutableLiveData<List<FollowResponseItem>>()
    val followingResponse: LiveData<List<FollowResponseItem>> = _followingResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "FollowViewModel"
    }

    fun followerUser(githubFollower: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowersUsers(githubFollower)
        with(client) {
            enqueue(object : Callback<List<FollowResponseItem>> {
                override fun onResponse(
                    call: Call<List<FollowResponseItem>>,
                    response: Response<List<FollowResponseItem>>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        _followerResponse.value = response.body()
                        Log.e(TAG, "onSuccess: ${response.message()}")
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<List<FollowResponseItem>>, t: Throwable) {
                    _isLoading.value = false
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }
            })
        }
    }

    fun followingUser(githubFollowing: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowersUsers(githubFollowing)
        with(client) {
            enqueue(object : Callback<List<FollowResponseItem>> {
                override fun onResponse(
                    call: Call<List<FollowResponseItem>>,
                    response: Response<List<FollowResponseItem>>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        _followingResponse.value = response.body()
                        Log.e(TAG, "onSuccess: ${response.message()}")
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<List<FollowResponseItem>>, t: Throwable) {
                    _isLoading.value = false
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }
            })
        }
    }

}