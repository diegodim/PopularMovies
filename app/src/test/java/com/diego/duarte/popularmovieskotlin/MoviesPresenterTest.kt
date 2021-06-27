package com.diego.duarte.popularmovieskotlin

import com.diego.duarte.popularmovieskotlin.data.model.Movie
import com.diego.duarte.popularmovieskotlin.data.model.Movies
import com.diego.duarte.popularmovieskotlin.data.source.Repository
import com.diego.duarte.popularmovieskotlin.movies.MoviesContract
import com.diego.duarte.popularmovieskotlin.movies.MoviesPresenter
import com.diego.duarte.popularmovieskotlin.util.TestSchedulerProvider
import io.reactivex.rxjava3.core.Observable
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList


class MoviesPresenterTest {


    private val movieViewMock: MoviesContract.View = mock( MoviesContract.View::class.java)
    private val repository: Repository = mock( Repository::class.java)
    private lateinit var objectUnderTest: MoviesContract.Presenter

    @Before
    @Throws(Exception::class)
    fun setUp() {
        objectUnderTest =  MoviesPresenter(repository, movieViewMock, TestSchedulerProvider())
    }

    @Test
    fun shouldLoadByPopularity(){
        //given
        val page = 1
        val movies = Movies(0, Arrays.asList(Movie(), Movie()), 0,0)
        given(repository.getMoviesByPopularity(page)).willReturn(Observable.just(Response.success(movies)))

        //when
        objectUnderTest.getMovies(0, page)

        //then
        then(movieViewMock).should(times(1)).showLoadingDialog()
        then(movieViewMock).should(times(1)).showMovies(movies.results)
        then(movieViewMock).should(times(1)).hideLoadingDialog()
        then(movieViewMock).shouldHaveNoMoreInteractions()
    }

    @Test
    fun shouldLoadByRate(){
        //given
        val page = 1
        val movies = Movies(0, Arrays.asList(Movie(), Movie()), 0,0)
        given(repository.getMoviesByRating(page)).willReturn(Observable.just(Response.success(movies)))

        //when
        objectUnderTest.getMovies(1, page)

        //then
        then(movieViewMock).should(times(1)).showLoadingDialog()
        then(movieViewMock).should(times(1)).showMovies(movies.results)
        then(movieViewMock).should(times(1)).hideLoadingDialog()
        then(movieViewMock).shouldHaveNoMoreInteractions()
    }

    @Test
    fun shouldLoadByFavorite(){
        //given
        val movies = Arrays.asList(Movie(), Movie())
        given(repository.getMoviesByFavorite()).willReturn(Observable.just(movies))

        //when
        objectUnderTest.getMovies(2, 1)

        //then
        then(movieViewMock).should(times(1)).showLoadingDialog()
        then(movieViewMock).should(times(1)).showMovies(movies)
        then(movieViewMock).should(times(1)).hideLoadingDialog()
        then(movieViewMock).shouldHaveNoMoreInteractions()
    }

    @Test
    fun shouldLoadNextPageByPopularity(){
        //given
        val movies = Movies(2, Arrays.asList(Movie(), Movie()), 0,0)
        given(repository.getMoviesByPopularity(2)).willReturn(Observable.just(Response.success(movies)))

        //when
        val page = objectUnderTest.loadNextPage(0, 1)

        //then
        assertTrue(page == 2)
        then(movieViewMock).should(times(1)).showMovies(movies.results)
        then(movieViewMock).should(times(1)).hideLoadingDialog()
        then(movieViewMock).shouldHaveNoMoreInteractions()
    }

    @Test
    fun shouldNotLoadByPopularity(){
        //given
        val page = 1
        given(repository.getMoviesByPopularity(page)).willReturn(Observable.just(Response.success(null)))

        //when
        objectUnderTest.getMovies(0, page)

        //then
        then(movieViewMock).should(times(1)).showLoadingDialog()
        then(movieViewMock).should(times(1)).showError("null")

        then(movieViewMock).shouldHaveNoMoreInteractions()
    }


    @Test
    fun shouldNotLoadByFavorite(){
        //given
        val movies = ArrayList<Movie>()
        given(repository.getMoviesByFavorite()).willReturn(Observable.just(movies))

        //when
        objectUnderTest.getMovies(2, 1)

        //then
        then(movieViewMock).should(times(1)).showLoadingDialog()
        then(movieViewMock).should(times(1)).showError("Index 0 out of bounds for length 0")

        then(movieViewMock).shouldHaveNoMoreInteractions()
    }

}