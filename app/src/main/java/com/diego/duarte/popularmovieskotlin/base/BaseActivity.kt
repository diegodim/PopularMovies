package com.diego.duarte.popularmovieskotlin.base

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.diego.duarte.popularmovieskotlin.R
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity

abstract class BaseActivity: DaggerAppCompatActivity() {

    private lateinit var viewError: View
    private lateinit var viewLoading: View
    private lateinit var buttonError: Button
    private lateinit var textError: TextView
    private lateinit var imageLoading: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(getContent())

        viewError = findViewById(R.id.view_error_layout)
        viewLoading = findViewById(R.id.view_loading_layout)
        textError = findViewById(R.id.view_error_txt_cause)
        buttonError = findViewById(R.id.view_error_btn_retry)
        buttonError.setOnClickListener { retryClick() }

    }

    protected fun hideLoading(){
        viewLoading.visibility = View.GONE
        viewError.visibility = View.GONE
    }

    protected fun showLoading(){
        imageLoading = findViewById(R.id.image_load)
        val pulse: Animation = AnimationUtils.loadAnimation(this, R.anim.pulse)
        imageLoading.startAnimation(pulse)
        viewLoading.visibility = View.VISIBLE
        viewError.visibility = View.GONE

    }

    protected fun onError(message: String){
        viewLoading.visibility = View.GONE
        viewError.visibility = View.VISIBLE
        textError.text = message
    }

    abstract fun retryClick()

    abstract fun getContent(): Int

    abstract fun getPresenter(): BasePresenter

    override fun onDestroy() {
        getPresenter().onDestroy()
        cacheDir.deleteRecursively()
        super.onDestroy()
    }

}