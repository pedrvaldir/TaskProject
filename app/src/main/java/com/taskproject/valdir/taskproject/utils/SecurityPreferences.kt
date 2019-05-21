package com.taskproject.valdir.taskproject.utils

import android.content.Context
import android.content.SharedPreferences

class SecurityPreferences (context: Context) {

    private val mSharedPreferences: SharedPreferences = context.getSharedPreferences("tasks", Context.MODE_PRIVATE)

    fun storeString(key: String, value: String){
        mSharedPreferences.edit().putString(key, value).apply()
    }

    fun getStoredString (key: String): String{
        return mSharedPreferences.getString(key, "")
    }

    fun removeStoredString(key: String){
        mSharedPreferences.edit().remove(key). apply()
    }
}