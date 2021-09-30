package spacekotlin.vaniukova.movies.data.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = TopMoviesContract.TABLE_TOP_MOVIES_NAME)
data class TopMovieDB(
    @PrimaryKey
    @ColumnInfo(name = TopMoviesContract.Columns.ID)
    val idString: String,
    @ColumnInfo(name = TopMoviesContract.Columns.TITLE)
    val title: String,
    @ColumnInfo(name = TopMoviesContract.Columns.YEAR)
    val year: String,
    @ColumnInfo(name = TopMoviesContract.Columns.TYPE)
    val type: String,
    @ColumnInfo(name = TopMoviesContract.Columns.POSTER)
    val poster: String,
    @ColumnInfo(name = TopMoviesContract.Columns.PLOT)
    val plot: String = "",
    @ColumnInfo(name = TopMoviesContract.Columns.IS_FAVOURITE)
    var isFavourite: Boolean = false
)
