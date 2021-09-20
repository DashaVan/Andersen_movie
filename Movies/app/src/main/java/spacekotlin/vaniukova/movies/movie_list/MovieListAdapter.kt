package spacekotlin.vaniukova.movies.movie_list

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class MovieListAdapter(
    onItemClicked: (id: Long) -> Unit
) : AsyncListDifferDelegationAdapter<Movie>(MovieDiffUtilCallback()) {

    init {
        delegatesManager.addDelegate(MovieDelegateAdapter(onItemClicked))
    }

    class MovieDiffUtilCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.idString.drop(2).toLong() == newItem.idString.drop(2).toLong()
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }
}