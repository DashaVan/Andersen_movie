package spacekotlin.vaniukova.movies.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import spacekotlin.vaniukova.movies.data.db.models.MovieDB
import spacekotlin.vaniukova.movies.data.db.models.MoviesContract

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieDB>)

    @Query("SELECT * FROM ${MoviesContract.TABLE_NAME}")
    suspend fun getAllFavouriteMovies(): List<MovieDB>

   /* @Query("SELECT * FROM ${MoviesContract.TABLE_NAME} WHERE ${MoviesContract.Columns.ID} = :id")
    suspend fun getMovieById(id: String): MovieDB?*/

    @Query("DELETE FROM ${MoviesContract.TABLE_NAME} WHERE ${MoviesContract.Columns.ID} = :id")
    suspend fun removeFavouriteMovieById(id: String)
}