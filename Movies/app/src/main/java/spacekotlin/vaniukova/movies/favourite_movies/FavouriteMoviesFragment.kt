package spacekotlin.vaniukova.movies.favourite_movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import spacekotlin.vaniukova.movies.MainActivity
import spacekotlin.vaniukova.movies.R
import spacekotlin.vaniukova.movies.databinding.FragmentTopMoviesBinding

class FavouriteMoviesFragment: Fragment(R.layout.fragment_top_movies) {
    private var _binding: FragmentTopMoviesBinding? = null
    private val binding get() = _binding!!

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
    }

}