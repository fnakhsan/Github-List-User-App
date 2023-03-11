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

    @Query("SELECT * FROM UserModel")
    suspend fun getAll(): List<UserModel>

    @Query("SELECT * FROM UserModel where UserModel.login = :name")
    fun getFav(name: String): UserModel

    @Query("SELECT COUNT(*) FROM UserModel where UserModel.login = :name")
    fun countFav(name: String): Int

    @Query("SELECT EXISTS(SELECT * FROM UserModel WHERE UserModel.login = :name)")
    fun isFavorite(name: String): Boolean

    @Update
    fun update(favorite: UserModel)

    @Delete
    suspend fun delete(favorite: UserModel)
}