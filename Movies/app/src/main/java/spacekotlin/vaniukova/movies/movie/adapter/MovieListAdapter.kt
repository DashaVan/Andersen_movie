package spacekotlin.vaniukova.movies.movie.adapter

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import spacekotlin.vaniukova.movies.movie.Movie

class MovieListAdapter(
    onItemClicked: (id: String) -> Unit
) : AsyncListDifferDelegationAdapter<Movie>(MovieDiffUtilCallback()) {

    init {
        delegatesManager.addDelegate(MovieDelegateAdapter(onItemClicked))
    }

    class MovieDiffUtilCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.idString == newItem.idString
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }
}