package spacekotlin.vaniukova.movies.network

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ServerItemsWrapper<T>(
    val Search: List<T>
)