package spacekotlin.vaniukova.movies.favourite_movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import spacekotlin.vaniukova.movies.MainActivity
import spacekotlin.vaniukova.movies.Navigator
import spacekotlin.vaniukova.movies.R
import spacekotlin.vaniukova.movies.data.db.models.MovieDB
import spacekotlin.vaniukova.movies.databinding.FragmentTopMoviesBinding
import spacekotlin.vaniukova.movies.detail_movie.DetailFragment
import spacekotlin.vaniukova.movies.movie.Movie
import spacekotlin.vaniukova.movies.movie.adapter.MovieListAdapter
import spacekotlin.vaniukova.movies.utils.autoCleared

class FavouriteMoviesFragment: Fragment(R.layout.fragment_top_movies) {
    private var _binding: FragmentTopMoviesBinding? = null
    private val binding get() = _binding!!

    private var favouriteMoviesAdapter: MovieListAdapter by autoCleared()
    private val favouriteMoviesViewModel: FavouriteMoviesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTopMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).setToolbarTitle("Favourites")

        bindViewModel()
        initList()
        favouriteMoviesViewModel.loadList()
    }

    private fun initList() {
        favouriteMoviesAdapter = MovieListAdapter { id -> openDetailFragment(id) }
        with(binding.recyclerViewTopMovies) {
            adapter = favouriteMoviesAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun openDetailFragment(id: String) {
        (activity as Navigator).navigateTo(DetailFragment.newInstance(id, true), "detailFragment")
    }

    private fun bindViewModel() {
        favouriteMoviesViewModel.favouriteMovies.observe(viewLifecycleOwner) { list ->
            favouriteMoviesAdapter.items = list.map { transformMovieDBtoMovie(it) }
        }
    }

    private fun transformMovieDBtoMovie(movieDB: MovieDB): Movie {
        return Movie(
            movieDB.idString,
            movieDB.title,
            movieDB.year,
            movieDB.type,
            movieDB.poster,
            movieDB.plot
        )
    }
}