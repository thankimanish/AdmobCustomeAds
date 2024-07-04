package com.theappcoderz.admobcustomeads

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri

class Prefs(context: Context, prefname: String) {
    private val sharedPreferences: SharedPreferences
    private val editor: SharedPreferences.Editor

    private fun convertUnderscoreToPeriod(input: String): String {
        return input.replace(".", "_")
    }

    init {
        sharedPreferences =
            context.getSharedPreferences(convertUnderscoreToPeriod(prefname), Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

    }

    fun setInt(key: String?, value: Int) {
        editor.putInt(key, value)
        editor.apply()
    }

    fun setString(key: String?, value: String?) {
        editor.putString(key, value)
        editor.apply()
    }

    fun setUri(key: String?, value: Uri) {
        editor.putString(key, value.toString())
        editor.apply()
    }


    fun setBoolean(key: String?, value: Boolean) {
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getBoolean(key: String?, def: Boolean): Boolean {
        return if(key?.let { contains(it) } == true)
        sharedPreferences.getBoolean(key, def)
        else false
    }

    fun getUri(key: String?, def: String?): Uri? {
        return if (key?.let { contains(it) } == true)
            Uri.parse(sharedPreferences.getString(key, def))
        else null
    }

    fun getInt(key: String?, def: Int): Int {
        return if (key?.let { contains(it) } == true)
            sharedPreferences.getInt(key, def)
        else 0
    }

    fun getString(key: String?, def: String): String? {
        return if (key?.let { contains(it) } == true)
            sharedPreferences.getString(key, def)
        else ""
    }

    fun contains(key: String): Boolean {
        return sharedPreferences.contains(key)
    }

}
