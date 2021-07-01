package com.diego.duarte.popularmovieskotlin.movie

import com.diego.duarte.popularmovieskotlin.base.BaseObserver
import com.diego.duarte.popularmovieskotlin.base.BasePresenter
import com.diego.duarte.popularmovieskotlin.data.model.Movie
import com.diego.duarte.popularmovieskotlin.data.model.Videos
import com.diego.duarte.popularmovieskotlin.data.source.Repository
import com.diego.duarte.popularmovieskotlin.movie.domain.DeleteFavoriteMovie
import com.diego.duarte.popularmovieskotlin.movie.domain.GetFavoriteMovie
import com.diego.duarte.popularmovieskotlin.movie.domain.GetVideos
import com.diego.duarte.popularmovieskotlin.movie.domain.SetFavoriteMovie
import com.diego.duarte.popularmovieskotlin.util.schedulers.SchedulerProvider
import retrofit2.Response

class MoviePresenter (repository: Repository,
                      private var movie: Movie,
                      private val view: MovieContract.View,
                      schedulerProvider: SchedulerProvider
)
    : BasePresenter(), MovieContract.Presenter{


    private val deleteFavorite = DeleteFavoriteMovie(repository,
        schedulerProvider.ui(),
        schedulerProvider.ui())

    private val getFavorite = GetFavoriteMovie(repository,
        schedulerProvider.trampoline(),
        schedulerProvider.ui())

    private val setFavorite = SetFavoriteMovie(repository,
        schedulerProvider.trampoline(),
        schedulerProvider.ui())

    private val getVideos = GetVideos(repository,
        schedulerProvider.io(),
        schedulerProvider.ui())

    override fun getMovie() {
        movie.isFavorite = false
        view.showLoadingDialog()
        view.showMovie(movie)
        getFavorite()
        getVideos()

    }

    override fun favorite(){
        if(!movie.isFavorite) {
            setFavorite()
        }else{
            deleteFavorite()
        }
    }

    private fun getVideos(){
        val disposable = getVideos.execute(VideosListObserver(),
            GetVideos.Params(movie.id!!))
        this.addDisposable(disposable)
    }

    private fun setFavorite(){
        movie.isFavorite = true
        val disposable = setFavorite.execute(MovieFavoriteObserver(),
            SetFavoriteMovie.Params(movie))
        this.addDisposable(disposable)
    }

    private fun deleteFavorite(){
        movie.isFavorite = false
        val disposable = deleteFavorite.execute(MovieFavoriteObserver(),
            DeleteFavoriteMovie.Params(movie))
        this.addDisposable(disposable)
    }

    private fun getFavorite(){

        val disposable = getFavorite.execute(MovieFavoriteObserver(),
            GetFavoriteMovie.Params(movie))
        this.addDisposable(disposable)
    }



    inner class MovieFavoriteObserver: BaseObserver<Movie>(){
        override fun onNext(t: Movie) {
            view.showFavorite(t.isFavorite)
            movie.isFavorite = t.isFavorite
        }
    }


    inner class VideosListObserver: BaseObserver<Response<Videos>>() {
        override fun onNext(t: Response<Videos>) {
            if(t.isSuccessful)
                view.showVideos(t.body()!!)
            else
                view.showError("") //TODO a enum of error
        }

        override fun onError(e: Throwable?) {
            //TODO a enum of error
            view.showError("")
        }

        override fun onComplete() {

            view.hideLoadingDialog()
        }

    }

}