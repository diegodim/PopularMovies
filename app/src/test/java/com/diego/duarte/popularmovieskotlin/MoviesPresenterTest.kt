package com.diego.duarte.popularmovieskotlin

import com.diego.duarte.popularmovieskotlin.fragments.movies.MoviesFragment
import com.diego.duarte.popularmovieskotlin.fragments.movies.MoviesPresenter
import com.diego.duarte.popularmovieskotlin.models.Movies
import io.reactivex.rxjava3.observers.TestObserver
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import retrofit2.Response

class MoviesPresenterTest {

    @Test
    fun movieApiIsLoadingCorrectData(){
        //given
        val objectUnderTest = MoviesPresenter()
        val testObserver = TestObserver<Response<Movies>>()

        //when
        val result = objectUnderTest.buildMovies( )
        result?.subscribe(testObserver)

        //then
        val listResult = testObserver.values()[0]
        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)
        assertThat(listResult.isSuccessful, `is`(true))
        assertThat(listResult?.body()?.results?.size, `is` (20))
    }
}