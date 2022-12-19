package com.example.movieproject.domain.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(tableName = "users")
data class User (
    @SerializedName("name")
    var name: String?,
   @SerializedName("username")
    var username: String?,
   @SerializedName("session_id")
    var sessionId: String?,
   @SerializedName("account_id")
    var accountId: Int?,
    @SerializedName("avatar_path")
    var avatar_path: String
)





