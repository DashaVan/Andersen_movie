package spacekotlin.vaniukova.movies.data.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = MoviesContract.TABLE_NAME)
data class MovieDB(
    @PrimaryKey
    @ColumnInfo(name = MoviesContract.Columns.ID)
    val idString: String,
    @ColumnInfo(name = MoviesContract.Columns.TITLE)
    val title: String,
    @ColumnInfo(name = MoviesContract.Columns.YEAR)
    val year: String,
    @ColumnInfo(name = MoviesContract.Columns.TYPE)
    val type: String,
    @ColumnInfo(name = MoviesContract.Columns.POSTER)
    val poster: String,
    @ColumnInfo(name = MoviesContract.Columns.PLOT)
    val plot: String = "",
    @ColumnInfo(name = MoviesContract.Columns.IS_FAVOURITE)
    var isFavourite: Boolean = true
)
