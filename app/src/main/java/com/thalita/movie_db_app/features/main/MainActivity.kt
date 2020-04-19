package com.thalita.movie_db_app.features.main

import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.thalita.movie_db_app.R
import com.thalita.movie_db_app.core.extension.invisible
import com.thalita.movie_db_app.core.extension.visible
import com.thalita.movie_db_app.features.home.HomeFragment
import com.thalita.movie_db_app.features.login.LoginFragment
import com.thalita.movie_db_app.features.profile.ProfileFragment
import com.thalita.movie_db_app.core.plataform.BaseActivity
import com.thalita.movie_db_app.features.movies.MyListFragment
import com.thalita.movie_db_app.features.movies.SearchFragment
import com.thalita.movie_db_app.core.plataform.UserAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private var navigationView: BottomNavigationView?=null
    private var userAuth: UserAuth?=null

    override fun setLayout() {
        hideTop(true)
        setContentView(R.layout.activity_main)
    }

    override fun getObjects() {
    }

    override fun setObjects() {
        navigationView = findViewById(R.id.navigation)
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        val navigationId = intent.getIntExtra(PARAM_NAVIGATION_ID, R.id.nav_home)
        navigation.selectedItemId = navigationId
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        userAuth =UserAuth(this)

        if(userAuth!!.isLogged()){
            loadFragment(item.itemId)
        }else {
            showMenuNavigation(false)
            replaceFragment(LoginFragment())
        }
        true
    }

    private fun loadFragment(itemId: Int) {
        val tag = itemId.toString()
        showMenuNavigation(true)
        supportFragmentManager.findFragmentByTag(tag) ?: when (itemId) {
            R.id.nav_home -> {
                replaceFragment(HomeFragment())
            }

            R.id.nav_search -> {
                replaceFragment(SearchFragment())
            }

            R.id.nav_mylist -> {
                replaceFragment(MyListFragment())
            }

            R.id.nav_setting -> {
                replaceFragment(ProfileFragment())
            }
        }
    }

    companion object {
        const val PARAM_NAVIGATION_ID = "navigation_id"
    }

    private fun showMenuNavigation(show: Boolean) {
        if(show){
            navigationView?.visible()
        }else{
            navigationView?.invisible()
        }
    }
}
