package com.example.movie.model.authorization

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager


public class LoginSharedPref {
    fun getSharedPreferences(ctx: Context?): SharedPreferences? {
        return PreferenceManager.getDefaultSharedPreferences(ctx)
    }

    fun setUserName(ctx: Context?, userName: String?) {
        val editor: SharedPreferences.Editor = getSharedPreferences(ctx)!!.edit()
        editor.putString(Companion.PREF_USER_NAME, userName)
        editor.commit()
    }

    fun getUserName(ctx: Context?): String? {
        return getSharedPreferences(ctx)!!.getString(Companion.PREF_USER_NAME, "")
    }

    fun clearUserName(ctx: Context?) {
        val editor: SharedPreferences.Editor = getSharedPreferences(ctx)!!.edit()
        editor.clear() //clear all stored data
        editor.commit()
    }

    companion object {
        private const val PREF_USER_NAME = "username"
    }
}