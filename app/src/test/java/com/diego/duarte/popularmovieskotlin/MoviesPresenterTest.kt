package com.diego.duarte.popularmovieskotlin

import com.diego.duarte.popularmovieskotlin.data.model.Movies
import com.diego.duarte.popularmovieskotlin.data.source.MoviesRepository
import com.diego.duarte.popularmovieskotlin.data.source.Repository
import com.diego.duarte.popularmovieskotlin.movie.MovieContract
import com.diego.duarte.popularmovieskotlin.movies.MoviesContract
import com.diego.duarte.popularmovieskotlin.movies.MoviesPresenter
import com.diego.duarte.popularmovieskotlin.util.TestSchedulerProvider
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.Rule
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import retrofit2.Response
import io.reactivex.android.plugins.RxAndroidPlugins
import net.bytebuddy.matcher.ElementMatchers.`is`
import org.junit.Assert
import org.junit.Assert.assertThat

class MoviesPresenterTest {




    val movieViewMock: MoviesContract.View = mock( MoviesContract.View::class.java)
    val repository: Repository = mock( Repository::class.java)
    val schedulers = TestSchedulerProvider()
    val objectUnderTest = MoviesPresenter(repository, movieViewMock, schedulers)

    @Test
    fun movieApiIsLoadingCorrectData(){
        //given
        val movies = Movies(0, ArrayList(), 0,0)
        given(repository.getMoviesByPopularity(1)).willReturn(Observable.just(Response.success(movies)))
        //when
        objectUnderTest.getMovies(0, 1)

        //then
//        Mockito.verify(movieViewMock).hideLoadingDialog()
        then(movieViewMock).should(times(1)).showLoadingDialog()
        then(movieViewMock).should(times(1)).showMovies(movies.results)
        then(movieViewMock).should(times(1)).hideLoadingDialog()
        then(movieViewMock).shouldHaveNoMoreInteractions()
    }
}