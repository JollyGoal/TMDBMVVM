package com.example.tmdbmvvm.single_movie_details

import androidx.lifecycle.LiveData
import com.example.tmdbmvvm.data.api.TheMovieDBInterface
import com.example.tmdbmvvm.data.repository.MovieDetailsNetworkDataSource
import com.example.tmdbmvvm.data.repository.NetworkState
import com.example.tmdbmvvm.data.vo.MovieDetails
import io.reactivex.disposables.CompositeDisposable

class MovieDetailsRepository(private val apiService: TheMovieDBInterface) {
    lateinit var movieDetailsNetworkDataSource: MovieDetailsNetworkDataSource

    fun fetchSingleMovieDetails(compositeDisposable: CompositeDisposable, movieId: Int) : LiveData<MovieDetails> {
        movieDetailsNetworkDataSource = MovieDetailsNetworkDataSource(apiService, compositeDisposable)
        movieDetailsNetworkDataSource.fetchMovieDetails(movieId)

        return movieDetailsNetworkDataSource.downloadedMovieResponse
    }

    fun getMovieDetails(): LiveData<NetworkState> {
        return movieDetailsNetworkDataSource.networkState
    }
}