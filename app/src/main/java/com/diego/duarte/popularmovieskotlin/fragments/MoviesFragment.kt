package com.diego.duarte.popularmovieskotlin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.diego.duarte.popularmovieskotlin.R
import com.diego.duarte.popularmovieskotlin.models.Movies
import com.diego.duarte.popularmovieskotlin.network.api.ApiService
import com.diego.duarte.popularmovieskotlin.network.api.RetrofitBuilder
import com.diego.duarte.popularmovieskotlin.views.adapters.MoviesAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * A simple [Fragment] subclass.
 * Use the [MoviesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MoviesFragment : Fragment() {
    private var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        recyclerView = activity?.findViewById(R.id.movies_recycler)
        val request = RetrofitBuilder().buildRetrofit(getString(R.string.api_url)).create(ApiService::class.java)
        val call = request.getMovies(getString(R.string.api_key), getString(R.string.api_language), 1)
        call.enqueue(object : Callback<Movies> {
            override fun onResponse(call: Call<Movies>, response: Response<Movies>) {
                if(response.isSuccessful){
                    recyclerView?.apply {
                        setHasFixedSize(true)
                        layoutManager = GridLayoutManager(activity, 3)
                        adapter = MoviesAdapter(response.body()!!.results)
                    }
                }
            }

            override fun onFailure(call: Call<Movies>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })

        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

}