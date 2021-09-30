package spacekotlin.vaniukova.movies

import android.app.Application
import android.content.SharedPreferences
import spacekotlin.vaniukova.movies.data.db.Database

class MovieApp: Application() {
    override fun onCreate() {
        super.onCreate()
        Database.init(this)
    }
}