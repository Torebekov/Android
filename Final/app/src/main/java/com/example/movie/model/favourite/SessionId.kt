package com.example.movie.model.favourite

import com.google.gson.annotations.SerializedName


class SessionId(token: String?) {
    @SerializedName("request_token")
    var requestToken: String = ""

    init {
        if (token != null) {
            requestToken = token
        }
    }
}