package com.example.githubuser3.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class UserModel(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "login")
    @field:SerializedName("login")
    var login: String = "",

    @ColumnInfo(name = "name")
    @field:SerializedName("name")
    var name: String?,

    @field:SerializedName("avatar_url")
    @ColumnInfo(name = "avatar_url")
    var avatar_url: String = "",

    @field:SerializedName("public_repos")
    @ColumnInfo(name = "public_repos")
    val repository: Int,

    @field:SerializedName("followers")
    @ColumnInfo(name = "followers")
    val followers: Int,

    @field:SerializedName("following")
    @ColumnInfo(name = "following")
    val following: Int,

    @field:SerializedName("html_url")
    @ColumnInfo(name = "html_url")
    val htmlUrl: String,

    @field:SerializedName("location")
    @ColumnInfo(name = "location")
    val location: String?,

    @field:SerializedName("company")
    @ColumnInfo(name = "company")
    val company: String?
) : Parcelable
