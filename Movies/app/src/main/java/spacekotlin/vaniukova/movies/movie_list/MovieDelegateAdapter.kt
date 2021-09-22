package spacekotlin.vaniukova.movies.movie_list

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import spacekotlin.vaniukova.movies.R
import spacekotlin.vaniukova.movies.inflate

class MovieDelegateAdapter(
    private val onItemClicked: (id: Long) -> Unit
) : AbsListItemAdapterDelegate<Movie, Movie, MovieDelegateAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        return Holder(parent.inflate(R.layout.item_movie), onItemClicked)
    }

    override fun isForViewType(
        item: Movie,
        items: MutableList<Movie>,
        position: Int
    ): Boolean = true

    override fun onBindViewHolder(item: Movie, holder: Holder, payloads: MutableList<Any>) {
        holder.bind(item)
    }

    class Holder(
        view: View,
        onItemClicked: (id: Long) -> Unit
    ) : RecyclerView.ViewHolder(view) {
        private val tvTitle: TextView = view.findViewById(R.id.textViewTitle)
        private val tvYear: TextView = view.findViewById(R.id.textViewYear)
        private val tvType: TextView = view.findViewById(R.id.textViewType)
        private val ivPoster: ImageView = view.findViewById(R.id.imageViewPoster)
        private var currentId: Long? = null

        init {
            view.setOnClickListener {
                currentId?.let { onItemClicked(it) }
            }
        }

        fun bind(movie: Movie) {
            currentId = movie.idString.drop(2).toLong()
            tvTitle.text = movie.title
            tvYear.text = movie.year
            tvType.text = movie.type

            Glide.with(itemView)
                .load(movie.poster)
                .placeholder(R.drawable.noposter)
                //.error(R.drawable.cat)
                .into(ivPoster)
        }
    }
}