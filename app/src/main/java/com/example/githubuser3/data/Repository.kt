package com.example.githubuser3.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.githubuser3.data.local.FavoriteDao
import com.example.githubuser3.data.model.FollowModel
import com.example.githubuser3.data.model.UserModel
import com.example.githubuser3.data.network.ApiService
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class Repository(
    private val apiService: ApiService,
    private val mFavDao: FavoriteDao,
) {
//    private val _searchResponse = MutableLiveData<List<UserModel>>()
//    val searchResponse: LiveData<List<UserModel>> = _searchResponse
//
//    private val _detailResponse = MutableLiveData<UserModel>()
//    val detailResponse: LiveData<UserModel> = _detailResponse
//
//    private val _followerResponse = MutableLiveData<List<FollowModel>>()
//    val followerResponse: LiveData<List<FollowModel>> = _followerResponse
//
//    private val _followingResponse = MutableLiveData<List<FollowModel>>()
//    val followingResponse: LiveData<List<FollowModel>> = _followingResponse

    //    private val mFavDao: FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

//    private val _isLoading = MutableLiveData<Boolean>()
//    val isLoading: LiveData<Boolean> = _isLoading

//    init {
//        val db = FavoriteDatabase.getDatabase(application)
//        mFavDao = db.favoriteDao()
//    }

    fun findUser(githubName: String): LiveData<Resource<List<UserModel>>> = liveData {
        emit(Resource.Loading)
        try {
            val response = apiService.getListUsers(githubName)
            emit(Resource.Success(response.items))
        } catch (e: Exception) {
            Log.d(TAG, "findUser: ${e.message.toString()} ")
            emit(Resource.Error(e.message.toString()))
        }
    }

    fun detailUser(githubProfile: String): LiveData<Resource<UserModel>> = liveData {
        emit(Resource.Loading)
        Log.d(TAG, "detailUser")
        try {
            if (mFavDao.isFavorite(githubProfile)) {
                emit(Resource.Success(mFavDao.getFav(githubProfile)))
            } else {
                val response = apiService.getDetailUsers(githubProfile)
                emit(Resource.Success(response))
            }
        } catch (e: Exception) {
            Log.d(TAG, "detailUser: ${e.message.toString()}")
            emit(Resource.Error(e.message.toString()))
        }
    }

    fun followerUser(githubFollower: String): LiveData<Resource<List<FollowModel>>> = liveData {
        emit(Resource.Loading)
        try {
            val response = apiService.getFollowersUsers(githubFollower)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            Log.d(TAG, "detailUser: ${e.message.toString()}")
            emit(Resource.Error(e.message.toString()))
        }
    }

    fun followingUser(githubFollowing: String): LiveData<Resource<List<FollowModel>>> = liveData {
        emit(Resource.Loading)
        try {
            val response = apiService.getFollowingUsers(githubFollowing)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            Log.d(TAG, "detailUser: ${e.message.toString()}")
            emit(Resource.Error(e.message.toString()))
        }
    }

//    fun findUser(githubName: String) {
//        _isLoading.value = true
//        val client = ApiConfig.getApiService().getListUsers(githubName)
//        with(client) {
//            enqueue(object : Callback<SearchModel> {
//                override fun onResponse(
//                    call: Call<SearchModel>,
//                    response: Response<SearchModel>
//                ) {
//                    _isLoading.value = false
//                    if (response.isSuccessful) {
//                        _searchResponse.value = response.body()?.items
//                        Log.d(TAG, "onSuccess: ${response.message()}, data: ${_searchResponse.value}")
//                    } else {
//                        Log.e(TAG, "onFailure: ${response.message()}")
//                    }
//                }
//
//                override fun onFailure(call: Call<SearchModel>, t: Throwable) {
//                    _isLoading.value = false
//                    Log.e(TAG, "onFailure: ${t.message.toString()}")
//                }
//            })
//        }
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
//
//    fun followerUser(githubFollower: String) {
//        _isLoading.value = true
//        val client = ApiConfig.getApiService().getFollowersUsers(githubFollower)
//        with(client) {
//            enqueue(object : Callback<List<FollowModel>> {
//                override fun onResponse(
//                    call: Call<List<FollowModel>>,
//                    response: Response<List<FollowModel>>
//                ) {
//                    _isLoading.value = false
//                    if (response.isSuccessful) {
//                        _followerResponse.value = response.body()
//                        Log.e(TAG, "onSuccess: ${response.message()}, data: ${_followerResponse.value}")
//                    } else {
//                        Log.e(TAG, "onFailure: ${response.message()}")
//                    }
//                }
//
//                override fun onFailure(call: Call<List<FollowModel>>, t: Throwable) {
//                    _isLoading.value = false
//                    Log.e(TAG, "onFailure: ${t.message.toString()}")
//                }
//            })
//        }
//    }
//
//    fun followingUser(githubFollowing: String) {
//        _isLoading.value = true
//        val client = ApiConfig.getApiService().getFollowingUsers(githubFollowing)
//        with(client) {
//            enqueue(object : Callback<List<FollowModel>> {
//                override fun onResponse(
//                    call: Call<List<FollowModel>>,
//                    response: Response<List<FollowModel>>
//                ) {
//                    _isLoading.value = false
//                    if (response.isSuccessful) {
//                        _followingResponse.value = response.body()
//                        Log.e(TAG, "onSuccess: ${response.message()}, data: ${_followingResponse.value}")
//                    } else {
//                        Log.e(TAG, "onFailure: ${response.message()}")
//                    }
//                }
//
//                override fun onFailure(call: Call<List<FollowModel>>, t: Throwable) {
//                    _isLoading.value = false
//                    Log.e(TAG, "onFailure: ${t.message.toString()}")
//                }
//            })
//        }
//    }

    suspend fun insert(favorite: UserModel) {
        mFavDao.insert(favorite)
    }

    fun getAllChanges(): LiveData<List<UserModel>> = mFavDao.getAllChanges()

    suspend fun getAll(): List<UserModel> = mFavDao.getAll()

    fun isFavorite(name: String): Boolean = mFavDao.isFavorite(name)

    fun countFav(name: String): Int = mFavDao.countFav(name)

    fun update(favorite: UserModel) {
        executorService.execute {
            mFavDao.update(favorite)
        }
    }

    suspend fun delete(favorite: UserModel) {
        mFavDao.delete(favorite)
    }

    companion object {
        private const val TAG = "Repository"

        @Volatile
        private var instance: Repository? = null
        fun getInstance(
            apiService: ApiService,
            favoriteDao: FavoriteDao,
        ): Repository = instance ?: synchronized(this) {
            instance ?: Repository(apiService, favoriteDao)
        }.also { instance = it }

    }
}