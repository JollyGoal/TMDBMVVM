package com.example.tmdbmvvm.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.tmdbmvvm.R
import com.example.tmdbmvvm.data.api.POSTER_BASE_URL
import com.example.tmdbmvvm.data.api.TheMovieDBClient
import com.example.tmdbmvvm.data.api.TheMovieDBInterface
import com.example.tmdbmvvm.data.repository.NetworkState
import com.example.tmdbmvvm.data.vo.MovieDetails
import com.example.tmdbmvvm.single_movie_details.MovieDetailsRepository
import com.example.tmdbmvvm.single_movie_details.SingleMovieViewModel
import kotlinx.android.synthetic.main.activity_single_movie.*
import java.text.NumberFormat
import java.util.*

class SingleMovieActivity : AppCompatActivity() {

    private lateinit var viewModel: SingleMovieViewModel
    private lateinit var movieRepository: MovieDetailsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_movie)

        val movieId = intent.getIntExtra("id", 1)

        val apiService: TheMovieDBInterface = TheMovieDBClient.getClient()
        movieRepository =
            MovieDetailsRepository(
                apiService
            )

        viewModel = getViewModel(movieId)

        viewModel.movieDetails.observe(this, Observer {
            bindUI(it)
        })

        viewModel.networkState.observe(this, Observer {
            single_movie_progress_bar.visibility =
                if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
            single_movie_error_text.visibility =
                if (it == NetworkState.ERROR) View.VISIBLE else View.GONE
        })
    }

    @SuppressLint("SetTextI18n")
    fun bindUI(it: MovieDetails) {
        single_movie_title.text = it.title
        single_movie_tagline.text = it.tagline
        single_movie_release_date.text = it.releaseDate
        single_movie_rating.text = it.voteAverage.toString()
        single_movie_runtime.text = it.runtime.toString() + "minutes"
        single_movie_overview.text = it.overview

        val formatCurrency = NumberFormat.getCurrencyInstance(Locale.US)
        single_movie_budget.text = formatCurrency.format(it.budget)
        single_movie_revenue.text = formatCurrency.format(it.revenue)

        val moviePosterUrl = POSTER_BASE_URL + it.posterPath
        Glide.with(this)
            .load(moviePosterUrl)
            .into(iv_movie_poster)
    }

    private fun getViewModel(movieId: Int): SingleMovieViewModel {
        return ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return SingleMovieViewModel(
                    movieRepository,
                    movieId
                ) as T
            }
        })[SingleMovieViewModel::class.java]
    }
}