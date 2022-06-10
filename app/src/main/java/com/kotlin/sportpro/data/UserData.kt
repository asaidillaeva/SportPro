package com.kotlin.sportpro.data

import android.content.Context
import com.google.gson.Gson


class UserData(var context: Context) {
    private val gson = Gson()
    private val prefsNode = "prefs"
    private val tokenNode = "token"
    private val phoneNode = "phone"
    private val idNode = "id"

    fun getToken(): String? {
        val myPrefs = context.getSharedPreferences(prefsNode, Context.MODE_PRIVATE)
        return myPrefs.getString(tokenNode, "")
    }

    fun saveToken(token: String) {
        val myPrefs = context.getSharedPreferences(prefsNode, Context.MODE_PRIVATE).edit()
        myPrefs.putString(tokenNode, token)
        myPrefs.apply()
    }

    fun getPhone(): String? {
        val myPrefs = context.getSharedPreferences(prefsNode, Context.MODE_PRIVATE)
        return myPrefs.getString(phoneNode, "")
    }

    fun savePhone(phone: String) {
        val myPrefs = context.getSharedPreferences(prefsNode, Context.MODE_PRIVATE).edit()
        myPrefs.putString(phoneNode, phone)
        myPrefs.apply()
    }

    fun getId(): Int {
        val myPrefs = context.getSharedPreferences(prefsNode, Context.MODE_PRIVATE)
        return myPrefs.getInt(idNode, 1)
    }

    fun saveId(id: Int) {
        val myPrefs = context.getSharedPreferences(prefsNode, Context.MODE_PRIVATE).edit()
        myPrefs.putInt(idNode, id)
        myPrefs.apply()
    }

    companion object {
        fun of(context: Context): UserData {
            return UserData(context)
        }
    }

}
