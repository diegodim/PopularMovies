package com.diego.duarte.popularmovieskotlin

import android.os.Bundle
import androidx.fragment.app.testing.launchFragment
import androidx.fragment.app.testing.launchFragmentInContainer
import com.diego.duarte.popularmovieskotlin.fragments.MovieFragment
import com.diego.duarte.popularmovieskotlin.fragments.MoviesFragment
import com.diego.duarte.popularmovieskotlin.models.Movies
import io.reactivex.rxjava3.observers.TestObserver
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import retrofit2.Response

class MoviesFragmentUnitTest {

    @Test
    fun movieApiIsLoadingCorrectData(){
        //given
        val objectUnderTest = MoviesFragment()
        val testObserver = TestObserver<Response<Movies>>()

        //when
        val result = objectUnderTest.buildMovies("https://api.themoviedb.org/3/", "e412d0c0cda73c4b13c5e6029f92547b","pt-BR",1)
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