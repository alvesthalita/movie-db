package com.thalita.movie_db_app.ui.activities

import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.thalita.movie_db_app.R
import com.thalita.movie_db_app.ui.activities.generic.GenericActivity
import com.thalita.movie_db_app.ui.fragments.HomeFragment
import com.thalita.movie_db_app.ui.fragments.LoginFragment
import com.thalita.movie_db_app.ui.fragments.MyListFragment
import com.thalita.movie_db_app.ui.fragments.SearchFragment
import com.thalita.movie_db_app.utils.UserAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : GenericActivity() {

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
        userAuth = UserAuth(this)

        if(userAuth!!.getUserLogged()!!){
            loadFragment(item.itemId)
        }else {
            navigationView?.visibility = View.GONE;
            replaceFragment(LoginFragment())
        }
        true
    }

    private fun loadFragment(itemId: Int) {
        val tag = itemId.toString()
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
                replaceFragment(MyListFragment())
            }
        }
    }

    companion object {
        const val PARAM_NAVIGATION_ID = "navigation_id"
    }
}
