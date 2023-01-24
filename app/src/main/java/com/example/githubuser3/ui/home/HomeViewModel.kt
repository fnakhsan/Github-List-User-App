package com.example.githubuser3.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser3.data.model.SearchModel
import com.example.githubuser3.data.model.UserModel
import com.example.githubuser3.data.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel: ViewModel() {
    private val _searchResponse = MutableLiveData<List<UserModel>>()
    val searchResponse: LiveData<List<UserModel>> = _searchResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    init {
        findUser("a")
    }

    fun findUser(githubName: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getListUsers(githubName)
        with(client) {
            enqueue(object : Callback<SearchModel> {
                override fun onResponse(
                    call: Call<SearchModel>,
                    response: Response<SearchModel>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        _searchResponse.value = response.body()?.items
                        Log.d(TAG, "onSuccess: ${response.message()}, data: ${_searchResponse.value}")
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<SearchModel>, t: Throwable) {
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