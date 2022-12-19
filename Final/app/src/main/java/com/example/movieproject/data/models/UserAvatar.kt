package com.example.movieproject.data.models

import java.io.Serializable
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


data class UserAvatar(
    @SerializedName("avatar")
    val avatar: Avatar,
    @PrimaryKey
    @SerializedName("id")
    val id: Int?,
    @SerializedName("include_adult")
    val include_adult: Boolean,
    @SerializedName("iso_3166_1")
    val iso_3166_1: String,
    @SerializedName("iso_639_3")
    val iso_639_1: String,
    @SerializedName("name")
    val name: String?,
    @SerializedName("username")
    val username: String?
) :Serializable