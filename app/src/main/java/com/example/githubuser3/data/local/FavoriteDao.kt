package com.example.githubuser3.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.githubuser3.data.model.FavoriteModel

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favorite: FavoriteModel)

    @Query("SELECT * FROM FavoriteModel")
    fun getAllChanges(): LiveData<List<FavoriteModel>>

    @Query("SELECT * FROM FavoriteModel")
    suspend fun getAll(): List<FavoriteModel>

    @Query("SELECT COUNT(*) FROM FavoriteModel where FavoriteModel.login = :name")
    fun countFav(name: String): Int

    @Update
    fun update(favorite: FavoriteModel)

    @Delete
    fun delete(favorite: FavoriteModel)
}