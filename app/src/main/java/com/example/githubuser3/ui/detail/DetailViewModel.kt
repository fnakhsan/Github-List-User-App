package com.example.githubuser3.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser3.data.model.UserModel
import com.example.githubuser3.data.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel: ViewModel() {
    private val _detailResponse = MutableLiveData<UserModel>()
    val detailResponse: LiveData<UserModel> = _detailResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun detailUser(githubProfile: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUsers(githubProfile)
        with(client) {
            enqueue(object : Callback<UserModel> {
                override fun onResponse(
                    call: Call<UserModel>,
                    response: Response<UserModel>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        _detailResponse.value = response.body()
                        Log.e(TAG, "onSuccess: ${response.message()}")
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<UserModel>, t: Throwable) {
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