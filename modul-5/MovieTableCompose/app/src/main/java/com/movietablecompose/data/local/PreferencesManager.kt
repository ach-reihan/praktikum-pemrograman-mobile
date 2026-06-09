package com.movietablecompose.data.local

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("movie_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_LAST_OPENED_MOVIE_ID = "last_opened_movie_id"
        private const val KEY_LAST_OPENED_MOVIE_TITLE = "last_opened_movie_title"
    }

    fun saveLastOpenedMovie(id: Int, title: String) {
        sharedPreferences.edit().apply {
            putInt(KEY_LAST_OPENED_MOVIE_ID, id)
            putString(KEY_LAST_OPENED_MOVIE_TITLE, title)
            apply()
        }
    }

    fun getLastOpenedMovieId(): Int {
        return sharedPreferences.getInt(KEY_LAST_OPENED_MOVIE_ID, -1)
    }

    fun getLastOpenedMovieTitle(): String? {
        return sharedPreferences.getString(KEY_LAST_OPENED_MOVIE_TITLE, null)
    }
}