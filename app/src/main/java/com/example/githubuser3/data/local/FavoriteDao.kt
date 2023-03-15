package com.example.githubuser3.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.githubuser3.data.model.UserModel

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favorite: UserModel)

    @Query("SELECT * FROM UserModel")
    fun getAllChanges(): LiveData<List<UserModel>>

    @Query("SELECT * FROM UserModel WHERE UserModel.login LIKE :name")
    fun searchFav(name: String): LiveData<List<UserModel>>

    @Query("SELECT * FROM UserModel WHERE UserModel.login = :name")
    fun getFav(name: String): UserModel

    @Query("SELECT EXISTS(SELECT * FROM UserModel WHERE UserModel.login = :name)")
    fun isFavorite(name: String): Boolean

    @Delete
    suspend fun delete(favorite: UserModel)
}