package com.thalita.movie_db_app.core.navigation

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.thalita.movie_db_app.R
import com.thalita.movie_db_app.features.main.MainActivity

class Navigator(private val context: Context){

    private var fm: FragmentManager? = null
    private var ft: FragmentTransaction? = null

    fun replaceFragment(fragment: Fragment) {
        fm!!.popBackStackImmediate()
        ft = fm!!.beginTransaction()
        ft!!.setCustomAnimations(R.anim.enter_anim, R.anim.exit_anim)
        ft!!.replace(R.id.main_contentframe, fragment)
        ft!!.commit()
    }

    fun replaceFragment(fragment: Fragment, layout: Int) {
        fm!!.popBackStackImmediate()
        ft = fm!!.beginTransaction()
        ft!!.setCustomAnimations(R.anim.enter_anim, R.anim.exit_anim)
        ft!!.replace(layout, fragment)
        ft!!.commit()
    }

    fun startActivity(activity: Activity, activityType: Class<*>, bundle: Bundle?) {
        val intent = Intent(context, activityType)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP

        if (bundle != null) {
            intent.putExtras(bundle)
        }

        activity.startActivity(intent)
    }

    fun openHome(activity: Activity){
        val intent=Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        activity.startActivity(intent)
    }
}