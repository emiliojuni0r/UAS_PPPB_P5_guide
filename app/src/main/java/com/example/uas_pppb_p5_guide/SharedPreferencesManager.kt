package com.example.uas_pppb_p5_guide

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesManager(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    fun saveLoginStatus(username: String, isAdmin: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putString("username", username)
        editor.putBoolean("isAdmin", isAdmin)
        editor.apply() // Simpan perubahan
    }

    fun checkLoginStatus(): Pair<String?, Boolean> {
        val username = sharedPreferences.getString("username", null)
        val isAdmin = sharedPreferences.getBoolean("isAdmin", false)
        return Pair(username, isAdmin)
    }

    fun logout() {
        val editor = sharedPreferences.edit()
        editor.clear() // Menghapus semua data
        editor.apply() // Simpan perubahan
    }
}