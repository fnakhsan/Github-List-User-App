package com.example.githubuser3.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubuser3.data.Repository
import com.example.githubuser3.data.model.UserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: Repository) : ViewModel() {

    fun isFavorite(name: String): Boolean = repository.isFavorite(name)

    fun getDetail(githubProfile: String) = repository.detailUser(githubProfile)

    fun setFavUser(user: UserModel) = viewModelScope.launch {
        repository.insert(user)
    }

    fun deleteFavUser(user: UserModel) = viewModelScope.launch {
        repository.delete(user)
    }
//    private val _detailResponse = MutableLiveData<UserModel>()
//    val detailResponse: LiveData<UserModel> = _detailResponse
//
//    private val _isLoading = MutableLiveData<Boolean>()
//    val isLoading: LiveData<Boolean> get() = _isLoading
//    private val repository: Repository = Repository(application)
//
//    fun insert(favorite: FavoriteModel) = viewModelScope.launch(Dispatchers.IO) {
//        repository.insert(favorite)
//    }
//
//    fun isFavorite(name: String): Boolean = repository.countFav(name) >= 1
//
//    fun delete(favorite: FavoriteModel) = viewModelScope.launch(Dispatchers.IO) {
//        repository.delete(favorite)
//    }
//
//    fun detailUser(githubProfile: String) {
//        _isLoading.value = true
//        val client = ApiConfig.getApiService().getDetailUsers(githubProfile)
//        with(client) {
//            enqueue(object : Callback<UserModel> {
//                override fun onResponse(
//                    call: Call<UserModel>,
//                    response: Response<UserModel>
//                ) {
//                    _isLoading.value = false
//                    if (response.isSuccessful) {
//                        _detailResponse.value = response.body()
//                        Log.e(TAG, "onSuccess: ${response.message()}")
//                    } else {
//                        Log.e(TAG, "onFailure: ${response.message()}")
//                    }
//                }
//
//                override fun onFailure(call: Call<UserModel>, t: Throwable) {
//                    _isLoading.value = false
//                    Log.e(TAG, "onFailure: ${t.message.toString()}")
//                }
//            })
//        }
//    }

    companion object {
        private const val TAG = "DetailViewModel"
    }
}