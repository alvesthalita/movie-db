package com.thalita.movie_db_app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.thalita.movie_db_app.R
import com.thalita.movie_db_app.ui.adapters.HomeViewPagerAdapter


class HomeFragment : Fragment() {

    private lateinit var viewPager: ViewPager
    private lateinit var pagerAdapter: HomeViewPagerAdapter
    private lateinit var homeBtn: LinearLayout
    private lateinit var myListBtn: LinearLayout
    private lateinit var watchedBtn: LinearLayout
    private lateinit var settingsBtn: LinearLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val view: View = inflater.inflate(
            R.layout.fragment_home, container,
            false)

        initComponent(view)
        return view
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

    fun initComponent(view: View){
        viewPager = view.findViewById(R.id.vpPager)
        pagerAdapter = HomeViewPagerAdapter(childFragmentManager, viewPager)
        homeBtn = view.findViewById(R.id.linear_home_btn)
        myListBtn = view.findViewById(R.id.linear_mylist_btn)
        watchedBtn = view.findViewById(R.id.linear_watched_btn)
        settingsBtn = view.findViewById(R.id.linear_settings_btn)

        initActions()
    }

    private fun initActions(){
        viewPager.adapter = pagerAdapter

        homeBtn.setOnClickListener {
            viewPager.currentItem = 0
        }

        myListBtn.setOnClickListener {
            viewPager.currentItem = 1
        }

        watchedBtn.setOnClickListener {
            viewPager.currentItem = 2
        }

        settingsBtn.setOnClickListener {
            viewPager.currentItem = 3
        }
    }

}
