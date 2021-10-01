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


    @Query("SELECT * FROM ${TopMoviesContract.TABLE_TOP_MOVIES_NAME} WHERE ${TopMoviesContract.Columns.YEAR} > :minYear")
    suspend fun getAllWithYearMoreThan(minYear: Int): List<TopMovieDB>

    @Query("SELECT * FROM ${TopMoviesContract.TABLE_TOP_MOVIES_NAME} ORDER BY ${TopMoviesContract.Columns.YEAR} DESC")
    suspend fun getAllWithYearDescending(): List<TopMovieDB>

    @Query("SELECT * FROM ${TopMoviesContract.TABLE_TOP_MOVIES_NAME} ORDER BY ${TopMoviesContract.Columns.YEAR} ASC")
    suspend fun getAllWithYearAscending(): List<TopMovieDB>
}