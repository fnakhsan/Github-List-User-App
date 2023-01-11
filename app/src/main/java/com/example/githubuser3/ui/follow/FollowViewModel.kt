package com.example.githubuser3.ui.follow

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser3.data.model.FollowModel
import com.example.githubuser3.data.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel: ViewModel() {
    private val _followerResponse = MutableLiveData<List<FollowModel>>()
    val followerResponse: LiveData<List<FollowModel>> = _followerResponse

    private val _followingResponse = MutableLiveData<List<FollowModel>>()
    val followingResponse: LiveData<List<FollowModel>> = _followingResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    fun followerUser(githubFollower: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowersUsers(githubFollower)
        with(client) {
            enqueue(object : Callback<List<FollowModel>> {
                override fun onResponse(
                    call: Call<List<FollowModel>>,
                    response: Response<List<FollowModel>>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        _followerResponse.value = response.body()
                        Log.e(TAG, "onSuccess: ${response.message()}, data: ${_followerResponse.value}")
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<List<FollowModel>>, t: Throwable) {
                    _isLoading.value = false
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }
            })
        }
    }

    fun followingUser(githubFollowing: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowingUsers(githubFollowing)
        with(client) {
            enqueue(object : Callback<List<FollowModel>> {
                override fun onResponse(
                    call: Call<List<FollowModel>>,
                    response: Response<List<FollowModel>>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        _followingResponse.value = response.body()
                        Log.e(TAG, "onSuccess: ${response.message()}, data: ${_followingResponse.value}")
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<List<FollowModel>>, t: Throwable) {
                    _isLoading.value = false
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }
            })
        }
    }

    companion object {
        private const val TAG = "FollowViewModel"
    }
}