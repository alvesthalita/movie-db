package com.thalita.movie_db_app.ui.activities

import com.thalita.movie_db_app.R
import com.thalita.movie_db_app.ui.activities.generic.GenericActivity
import com.thalita.movie_db_app.ui.fragments.HomeFragment
import com.thalita.movie_db_app.ui.fragments.LoginFragment

class MainActivity : GenericActivity() {

    private var userLogged: Boolean = false

    override fun setLayout() {
        hideTop(true)
        setContentView(R.layout.activity_main)
    }

    override fun getObjects() {
    }

    override fun setObjects() {
        init()
    }

    private fun init(){
        userLogged = true
//        userLogged = intent.hasExtra("userLogged")

        if(userLogged){
            replaceFragment(HomeFragment())
        }else {
            replaceFragment(LoginFragment())
        }
    }
}
