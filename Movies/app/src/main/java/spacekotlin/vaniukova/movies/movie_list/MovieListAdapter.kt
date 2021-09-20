package spacekotlin.vaniukova.movies.movie_list

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import spacekotlin.vaniukova.movies.R
import spacekotlin.vaniukova.movies.inflate

class MovieListAdapter(
    private val onItemClicked: (id: Long) -> Unit
) : RecyclerView.Adapter<MovieListAdapter.Holder>() {

    private val differ = AsyncListDiffer<Movie>(this, MovieDiffUtilCallBack())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(parent.inflate(R.layout.item_movie), onItemClicked)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val movie = differ.currentList[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int = differ.currentList.size

    class MovieDiffUtilCallBack : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }

    class Holder(
        view: View,
        onItemClicked: (id: Long) -> Unit
    ) : RecyclerView.ViewHolder(view) {
        private val tvTitle: TextView = view.findViewById(R.id.textViewTitle)
        private val tvYear: TextView = view.findViewById(R.id.textViewYear)
        private val ivPoster: ImageView = view.findViewById(R.id.imageViewPoster)
        private var currentId: Long? = null

        init {
            view.setOnClickListener {
                currentId?.let { onItemClicked(it) }
            }
        }

        fun bind(movie: Movie) {
            currentId = movie.id
            tvTitle.text = movie.title
            tvYear.text = movie.year

            Glide.with(itemView)
                .load(movie.poster)
                .placeholder(R.drawable.noposter)
                //.error(R.drawable.cat)
                .into(ivPoster)
        }
    }

    fun updateMovie(newMovies: List<Movie>) {
        differ.submitList(newMovies)
    }
}