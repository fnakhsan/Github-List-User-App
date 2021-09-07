package com.example.githubuser

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var username: String = "",
    var name: String = "",
    var company: String = "",
    var location: String = "",
    var repository: String = "",
    var follower: String = "",
    var following: String = "",
    var avatar: Int = 0
): Parcelable
