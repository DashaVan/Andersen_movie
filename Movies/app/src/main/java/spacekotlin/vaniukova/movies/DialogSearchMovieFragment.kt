package spacekotlin.vaniukova.movies

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import spacekotlin.vaniukova.movies.databinding.DialogSearchMovieFragmentBinding
import spacekotlin.vaniukova.movies.movie_list.QueryMovie

class DialogSearchMovieFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = DialogSearchMovieFragmentBinding.inflate(LayoutInflater.from(context))
        val builder = AlertDialog.Builder(
            requireContext()
        )
        builder
            .setView(binding.root)

        binding.ibCancel.setOnClickListener {
            dismiss()
        }

        var searchType = ""

        binding.radioGroup.setOnCheckedChangeListener { _, i ->
           searchType = when (i) {
                binding.radioBtnMovies.id -> "movie"
                binding.radioBtnSeries.id -> "series"
                binding.radioBtnGames.id -> "game"
                else -> ""
            }
        }

        binding.buttonSearch.setOnClickListener {
            if (binding.editTextTitle.text.isNotEmpty()) {
                val searchByTitle = binding.editTextTitle.text.toString()
                val searchYear = binding.editTextYear.text.toString()
                (parentFragment as QueryMovie).query(searchByTitle, searchYear, searchType, 1)
                dismiss()
            } else {
                Toast.makeText(
                    requireContext(),
                    R.string.dialog_message_fill_title,
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
        return builder.create()
    }
}