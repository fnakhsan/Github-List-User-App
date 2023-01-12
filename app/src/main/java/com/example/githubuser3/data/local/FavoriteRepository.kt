package com.example.githubuser3.data.local

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.githubuser3.data.model.FavoriteModel
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {
    private val mFavDao: FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteDatabase.getDatabase(application)
        mFavDao = db.favoriteDao()
    }

    fun insert(favorite: FavoriteModel) {
        executorService.execute {
            mFavDao.insert(favorite)
        }
    }

    fun getAllChanges(): LiveData<List<FavoriteModel>> = mFavDao.getAllChanges()

    suspend fun getAll(): List<FavoriteModel> = mFavDao.getAll()

    fun countFav(name: String): Int = mFavDao.countFav(name)

    fun update(favorite: FavoriteModel) {
        executorService.execute {
            mFavDao.update(favorite)
        }
    }

    fun delete(Language: FavoriteModel) {
        executorService.execute {
            mFavDao.delete(Language)
        }
    }
}