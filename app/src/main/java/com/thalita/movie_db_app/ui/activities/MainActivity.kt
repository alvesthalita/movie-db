package com.thalita.movie_db_app.ui.activities

import com.thalita.movie_db_app.R
import com.thalita.movie_db_app.ui.activities.generic.GenericActivity
import com.thalita.movie_db_app.ui.fragments.HomeFragment
import com.thalita.movie_db_app.ui.fragments.LoginFragment
import com.thalita.movie_db_app.utils.UserAuth

class MainActivity : GenericActivity() {

    private var userAuth: UserAuth?=null

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
        userAuth = UserAuth(this)

        if(userAuth!!.getUserLogged()!!){
            replaceFragment(HomeFragment())
        }else {
            replaceFragment(LoginFragment())
        }
    }
}
