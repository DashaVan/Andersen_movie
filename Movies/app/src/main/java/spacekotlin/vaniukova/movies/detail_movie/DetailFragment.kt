package spacekotlin.vaniukova.movies.detail_movie

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import spacekotlin.vaniukova.movies.MainActivity
import spacekotlin.vaniukova.movies.R
import spacekotlin.vaniukova.movies.databinding.FragmentDetailBinding
import spacekotlin.vaniukova.movies.movie.Movie
import spacekotlin.vaniukova.movies.withArguments

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val detailFragmentViewModel: DetailFragmentViewModel by viewModels()
    private var dMovie: Movie? = null
    private var deleteFavMovieDialog: AlertDialog? = null

    companion object {
        private const val KEY = "key"
        private const val KEY_FAV = "keyFav"
        fun newInstance(id: String, isFavourite: Boolean): DetailFragment {
            return DetailFragment().withArguments {
                putString(KEY, id)
                putBoolean(KEY_FAV, isFavourite)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).setToolbarTitle("Detailed information")

        val idString = requireArguments().getString(KEY)
        var isFavouriteMovie = requireArguments().getBoolean(KEY_FAV)

        bindViewModel()
        queryById(idString!!)

        binding.imageButtonStar.setOnClickListener{
            isFavouriteMovie = if (!isFavouriteMovie) {
                addFavourites()
                true
            } else {
                showDeleteFavMovDialog()
                false
            }
        }

        if (isFavouriteMovie) {
            binding.imageButtonStar.setImageResource(R.drawable.ic_star_yellow)
        }
    }

    private fun bindViewModel() {
        with(detailFragmentViewModel) {
            isLoading.observe(viewLifecycleOwner, ::updateLoadingState)
            detailMovie.observe(viewLifecycleOwner) { dMovie = it }
        }
    }

    private fun updateLoadingState(isLoading: Boolean) {
        with(binding) {
            ivDetailPoster.isVisible = isLoading.not()
            textView7.isVisible = isLoading.not()
            textView8.isVisible = isLoading.not()
            imageButtonStar.isVisible = isLoading.not()
            progressBar.isVisible = isLoading
        }
        if (!isLoading) {
            dMovie?.let { addInfo(it) }
        }
    }

    private fun queryById(id: String) {
        detailFragmentViewModel.requestMovieById(id)
    }

    private fun addInfo(movie: Movie) {
        with(binding) {
            tvDetailTitle.text = movie.title
            tvDetailType.text = movie.type
            tvDetailYear.text = movie.year
            tvPlot.text = movie.plot
        }

        Glide.with(this)
            .load(movie.poster)
            .placeholder(R.drawable.noposter)
            .into(binding.ivDetailPoster)
    }

    private fun addFavourites(){
        detailFragmentViewModel.save(dMovie!!)
        Toast.makeText(requireContext(), R.string.added_favourites, Toast.LENGTH_SHORT).show()
        binding.imageButtonStar.setImageResource(R.drawable.ic_star_yellow)
    }

    private fun removeFavouriteMovie(){
        detailFragmentViewModel.removeFavouriteMovie(dMovie!!)
        Toast.makeText(requireContext(), "Movie removed", Toast.LENGTH_SHORT).show()
        binding.imageButtonStar.setImageResource(R.drawable.ic_star)
    }

    private fun showDeleteFavMovDialog() {
        deleteFavMovieDialog = AlertDialog.Builder(requireContext())
            .setMessage("Delete this movie from favorites?")
            .setPositiveButton("Yes"){ _, _ ->
                removeFavouriteMovie()
            }
            .setNegativeButton("No", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        deleteFavMovieDialog?.dismiss()
    }
}