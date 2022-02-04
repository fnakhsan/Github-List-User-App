package com.example.githubuser2.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser2.data.SearchResponse
import com.example.githubuser2.data.UserResponse
import com.example.githubuser2.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val _searchResponse = MutableLiveData<List<UserResponse>>()
    val searchResponse: LiveData<List<UserResponse>> = _searchResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    init {
        findUser("a")
    }

    fun findUser(githubName: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getListUsers(githubName)
        with(client) {
            enqueue(object : Callback<SearchResponse> {
                override fun onResponse(
                    call: Call<SearchResponse>,
                    response: Response<SearchResponse>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        _searchResponse.value = response.body()?.items
                        Log.d(TAG, "onSuccess: ${response.message()}, data: ${_searchResponse.value}")
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                    _isLoading.value = false
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }
            })
        }
    }

    companion object {
        private const val TAG = "HomeViewModel"
    }

}
