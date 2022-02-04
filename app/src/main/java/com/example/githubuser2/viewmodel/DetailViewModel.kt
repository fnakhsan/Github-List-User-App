package com.example.githubuser2.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser2.data.UserResponse
import com.example.githubuser2.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {
    private val _detailResponse = MutableLiveData<UserResponse>()
    val detailResponse: LiveData<UserResponse> = _detailResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading


    fun detailUser(githubProfile: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUsers(githubProfile)
        with(client) {
            enqueue(object : Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        _detailResponse.value = response.body()
                        Log.e(TAG, "onSuccess: ${response.message()}")
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    _isLoading.value = false
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }
            })
        }
    }

    companion object {
        private const val TAG = "DetailViewModel"
    }

}
