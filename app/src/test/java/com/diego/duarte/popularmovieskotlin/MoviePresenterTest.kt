package com.diego.duarte.popularmovieskotlin

import com.diego.duarte.popularmovieskotlin.data.model.Movie
import com.diego.duarte.popularmovieskotlin.data.model.Video
import com.diego.duarte.popularmovieskotlin.data.model.Videos
import com.diego.duarte.popularmovieskotlin.data.source.Repository
import com.diego.duarte.popularmovieskotlin.movie.MovieContract
import com.diego.duarte.popularmovieskotlin.movie.MoviePresenter
import com.diego.duarte.popularmovieskotlin.util.schedulers.TestSchedulerProvider
import io.reactivex.rxjava3.core.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.Mockito
import retrofit2.Response

class MoviePresenterTest {

    private val movieViewMock: MovieContract.View = Mockito.mock(MovieContract.View::class.java)
    private val repository: Repository = Mockito.mock(Repository::class.java)
    private lateinit var objectUnderTest: MovieContract.Presenter
    private val movie = Movie()

    @Before
    @Throws(Exception::class)
    fun setUp() {
        objectUnderTest =  MoviePresenter(repository, movie,
            movieViewMock, TestSchedulerProvider())
    }

    @Test
    fun shouldGetMovieData(){
        //given
        movie.isFavorite = true
        movie.id = 0
        val videos = Videos(0, ArrayList())
        given(repository.getMovieFromFavorite(movie))
            .willReturn(Observable.just(movie))
        given(repository.getMovieVideos(movie.id!!))
            .willReturn(Observable.just(Response.success(videos)))

        //when
        objectUnderTest.getMovie()

        //then
        then(movieViewMock).should(Mockito.times(1)).showLoadingDialog()
        then(movieViewMock).should(Mockito.times(1)).showMovie(movie)
        then(movieViewMock).should(Mockito.times(1)).showFavorite(movie.isFavorite)
        then(movieViewMock).should(Mockito.times(1)).showVideos(videos)
        then(movieViewMock).should(Mockito.times(1)).hideLoadingDialog()
        then(movieViewMock).shouldHaveNoMoreInteractions()
    }

    @Test
    fun shouldFavorite(){
        //given
        movie.isFavorite = false
        given(repository.saveMovieAsFavorite(movie))
            .willReturn(Observable.just(true))

        //when
        objectUnderTest.favorite()

        //then
        then(movieViewMock).should(Mockito.times(1))
            .showFavorite(true)
        then(movieViewMock).shouldHaveNoMoreInteractions()
    }

    @Test
    fun shouldDeleteFavorite(){
        //given
        movie.isFavorite = true
        given(repository.deleteMovieAsFavorite(movie))
            .willReturn(Observable.just(false))

        //when
        objectUnderTest.favorite()

        //then
        then(movieViewMock).should(Mockito.times(1))
            .showFavorite(false)
        then(movieViewMock).shouldHaveNoMoreInteractions()
    }
}