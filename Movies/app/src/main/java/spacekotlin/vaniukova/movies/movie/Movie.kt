package spacekotlin.vaniukova.movies.movie

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.IgnoredOnParcel

@JsonClass(generateAdapter = true)
data class Movie(
    @Json(name = "imdbID")
    val idString: String,
    @Json(name = "Title")
    val title: String,
    @Json(name = "Year")
    val year: String,
    @Json(name = "Type")
    val type: String,
    @Json(name = "Poster")
    val poster: String,
    @Json(name = "Plot")
    val plot: String = "",
    @IgnoredOnParcel
    var isFavourite: Boolean = false
)
