package com.example.githubuser3.data.model

import com.google.gson.annotations.SerializedName

data class SearchModel(
    @field:SerializedName("total_count")
    val totalCount: Int,

    @field:SerializedName("incomplete_results")
    val incompleteResults: Boolean,

    @field:SerializedName("items")
    val items: List<UserModel>
)
