package spacekotlin.vaniukova.movies.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import spacekotlin.vaniukova.movies.data.db.models.MovieDB
import spacekotlin.vaniukova.movies.data.db.models.TopMovieDB

@Database(entities = [MovieDB::class, TopMovieDB::class], version = MoviesDatabase.DB_VERSION)
abstract class MoviesDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun topMovieDao(): TopMovieDao

    companion object {
        const val DB_VERSION = 1
        const val DB_NAME = "movies_database"
    }
}