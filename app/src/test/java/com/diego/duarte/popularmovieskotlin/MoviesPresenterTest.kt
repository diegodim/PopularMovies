package com.diego.duarte.popularmovieskotlin

import com.diego.duarte.popularmovieskotlin.data.model.Movie
import com.diego.duarte.popularmovieskotlin.data.model.Movies
import com.diego.duarte.popularmovieskotlin.data.source.Repository
import com.diego.duarte.popularmovieskotlin.movies.MoviesContract
import com.diego.duarte.popularmovieskotlin.movies.MoviesPresenter
import com.diego.duarte.popularmovieskotlin.util.schedulers.TestSchedulerProvider
import io.reactivex.rxjava3.core.Observable
import junit.framework.Assert.assertTrue
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import retrofit2.Response
import kotlin.collections.ArrayList


class MoviesPresenterTest {


    private val moviesViewMock: MoviesContract.View = mock( MoviesContract.View::class.java)
    private val repository: Repository = mock( Repository::class.java)
    private lateinit var objectUnderTest: MoviesContract.Presenter

    @Before
    @Throws(Exception::class)
    fun setUp() {
        objectUnderTest =  MoviesPresenter(repository, moviesViewMock, TestSchedulerProvider())
    }

    @Test
    fun shouldLoadByPopularity(){
        //given
        val page = 1
        val movies = Movies(0, listOf(Movie(), Movie()), 0,0)
        given(repository.getMoviesByPopularity(page)).willReturn(Observable.just(Response.success(movies)))

        //when
        objectUnderTest.getMovies(0, page)

        //then
        then(moviesViewMock).should(times(1)).showLoadingDialog()
        then(moviesViewMock).should(times(1)).showMovies(movies.results)
        then(moviesViewMock).should(times(1)).hideLoadingDialog()
        then(moviesViewMock).shouldHaveNoMoreInteractions()
    }

    @Test
    fun shouldLoadByRate(){
        //given
        val page = 1
        val movies = Movies(0, listOf(Movie(), Movie()), 0,0)
        given(repository.getMoviesByRating(page)).willReturn(Observable.just(Response.success(movies)))

        //when
        objectUnderTest.getMovies(1, page)

        //then
        then(moviesViewMock).should(times(1)).showLoadingDialog()
        then(moviesViewMock).should(times(1)).showMovies(movies.results)
        then(moviesViewMock).should(times(1)).hideLoadingDialog()
        then(moviesViewMock).shouldHaveNoMoreInteractions()
    }

    @Test
    fun shouldLoadByFavorite(){
        //given
        val movies = listOf(Movie(), Movie())
        given(repository.getMoviesByFavorite()).willReturn(Observable.just(movies))

        //when
        objectUnderTest.getMovies(2, 1)

        //then
        then(moviesViewMock).should(times(1)).showLoadingDialog()
        then(moviesViewMock).should(times(1)).showMovies(movies)
        then(moviesViewMock).should(times(1)).hideLoadingDialog()
        then(moviesViewMock).shouldHaveNoMoreInteractions()
    }

    @Test
    fun shouldLoadNextPageByPopularity(){
        //given
        val movies = Movies(2, listOf(Movie(), Movie()), 0,0)
        given(repository.getMoviesByPopularity(2)).willReturn(Observable.just(Response.success(movies)))

        //when
        val page = objectUnderTest.loadNextPage(0, 1)

        //then
        assertTrue(page == 2)
        then(moviesViewMock).should(times(1)).showMovies(movies.results)
        then(moviesViewMock).should(times(1)).hideLoadingDialog()
        then(moviesViewMock).shouldHaveNoMoreInteractions()
    }

    @Test
    fun shouldNotLoadByPopularity(){
        //given
        val page = 1
        val response = "".toResponseBody()
        given(repository.getMoviesByPopularity(page)).willReturn(Observable.just(Response.error(404, response)))

        //when
        objectUnderTest.getMovies(0, page)

        //then
        then(moviesViewMock).should(times(1)).showLoadingDialog()
        then(moviesViewMock).should(times(1)).showError("")

        then(moviesViewMock).shouldHaveNoMoreInteractions()
    }


    @Test
    fun shouldNotLoadByFavorite(){
        //given
        val movies: List<Movie> = ArrayList()
        given(repository.getMoviesByFavorite()).willReturn(Observable.just(movies))

        //when
        objectUnderTest.getMovies(2, 1)

        //then
        then(moviesViewMock).should(times(1)).showLoadingDialog()
        then(moviesViewMock).should(times(1)).showError("")

        then(moviesViewMock).shouldHaveNoMoreInteractions()
    }

}