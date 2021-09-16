package spacekotlin.vaniukova.movies

import androidx.fragment.app.Fragment

interface Navigator {
    fun navigateTo(fragment: Fragment, name: String)
}