package spacekotlin.vaniukova.movies.data.db.models

object MoviesContract {
    const val TABLE_NAME = "movies"

    object Columns {
        const val ID = "idString"
        const val TITLE = "title"
        const val YEAR = "year"
        const val TYPE = "type"
        const val POSTER = "poster"
        const val PLOT = "plot"
        const val IS_FAVOURITE = "isFavourite"
    }
}