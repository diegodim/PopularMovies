package com.diego.duarte.popularmovieskotlin.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.diego.duarte.popularmovieskotlin.R
import com.diego.duarte.popularmovieskotlin.fragments.movies.MoviesFragment

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val fragment = MoviesFragment()
        fragmentTransaction.add(R.id.main_fragment, fragment)
        fragmentTransaction.commit()
    }


}