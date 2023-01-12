package com.example.githubuser3.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class FavoriteModel(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "login")
    var username: String = "",

    @ColumnInfo(name = "avatar_url")
    var avatar: String = "",
): Parcelable
