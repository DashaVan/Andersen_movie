package spacekotlin.vaniukova.movies.top_movies_list

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import spacekotlin.vaniukova.movies.R
import spacekotlin.vaniukova.movies.databinding.DialogFilterFragmentBinding

class DialogFilterFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = DialogFilterFragmentBinding.inflate(LayoutInflater.from(context))
        val builder = AlertDialog.Builder(
            requireContext()
        )
        builder
            .setView(binding.root)

        binding.ibCancel.setOnClickListener {
            dismiss()
        }

        var rbId = 1
        var isChecked = false

        binding.etMinYear.isVisible = false

        binding.rgFilter.setOnCheckedChangeListener { _, i ->
            when (i) {
                binding.rbMinYear.id -> {
                    rbId = 1
                    binding.etMinYear.isVisible = true
                }
                binding.rbDescending.id -> rbId = 2
                binding.rbAscending.id -> rbId = 3
                else -> rbId = 4
            }
            isChecked = true
        }

        binding.btnFilter.setOnClickListener {
            if (isChecked) {
                when (rbId) {
                    1 -> {
                        if (binding.etMinYear.text.isNotEmpty()){
                            val year = binding.etMinYear.text.toString().toInt()
                            (parentFragment as FilterTopMovie).filterWithYear(year)
                            dismiss()
                        }else{
                            Toast.makeText(requireContext(), "Choose the minimum year", Toast.LENGTH_SHORT).show()
                        }
                    }
                    2 -> {
                        (parentFragment as FilterTopMovie).filterDes()
                        dismiss()
                    }
                    3 -> {
                        (parentFragment as FilterTopMovie).filterAsc()
                        dismiss()
                    }
                    else -> Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
                }

            } else {
                Toast.makeText(
                    requireContext(),
                    R.string.no_filter_selected,
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
        return builder.create()
    }
}