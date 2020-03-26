package com.thalita.movie_db_app.features.splash

import android.os.Handler
import com.thalita.movie_db_app.R
import com.thalita.movie_db_app.core.plataform.BaseActivity
import com.thalita.movie_db_app.features.main.MainActivity

class SplashActivity : BaseActivity() {

    private val SPLASH_TIME_OUT: Long=3000 // 3 sec

    override fun setLayout() {
        hideTop(true)
        setContentView(R.layout.activity_splash)
    }

    override fun getObjects() {
    }

    override fun setObjects() {
        startApp()
    }

    fun startApp(){
        Handler().postDelayed({
            // This method will be executed once the timer is over
            // Start your app main activity

            startActivity(MainActivity::class.java, null)
            finish()
        }, SPLASH_TIME_OUT)
    }
}
