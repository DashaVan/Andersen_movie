package spacekotlin.vaniukova.movies.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import spacekotlin.vaniukova.movies.data.db.models.TopMovieDB
import spacekotlin.vaniukova.movies.data.db.models.TopMoviesContract

@Dao
interface TopMovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<TopMovieDB>)

    @Query("SELECT * FROM ${TopMoviesContract.TABLE_TOP_MOVIES_NAME}")
    suspend fun getAllTopMovies(): List<TopMovieDB>
}