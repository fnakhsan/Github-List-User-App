package com.example.githubuser3.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.githubuser3.data.local.FavoriteDao
import com.example.githubuser3.data.model.FollowModel
import com.example.githubuser3.data.model.UserModel
import com.example.githubuser3.data.network.ApiService
import kotlinx.coroutines.Dispatchers
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class Repository(
    private val apiService: ApiService,
    private val mFavDao: FavoriteDao,
) {
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

    fun detailUser(githubProfile: String): LiveData<Resource<UserModel>> = liveData(Dispatchers.IO) {
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
//        val localData: LiveData<Result<List<NewsEntity>>> =
//            newsDao.getNews().map { Result.Success(it) }
//        emitSource(localData)
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

    suspend fun insert(favorite: UserModel) {
        mFavDao.insert(favorite)
    }

    fun getAllChanges(): LiveData<List<UserModel>> = mFavDao.getAllChanges()

    fun searchFav(name: String): LiveData<List<UserModel>> = mFavDao.searchFav(name)

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