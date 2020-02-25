package com.thalita.movie_db_app.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.thalita.movie_db_app.ui.fragments.MyListFragment
import com.thalita.movie_db_app.ui.fragments.SearchFragment
import com.thalita.movie_db_app.ui.fragments.SettingsFragment
import com.thalita.movie_db_app.ui.fragments.WatchedFragment

@Suppress("DEPRECATION")
class HomeViewPagerAdapter(fragmentManager: FragmentManager, private var viewPager: ViewPager) :
    FragmentStatePagerAdapter(fragmentManager) {

    // 2
    override fun getItem(position: Int): Fragment {
//        viewPager.addOnPageChangeListener(pageChangeListener)

        return when (position) {
            0 -> SearchFragment()
            1 -> MyListFragment()
            2 -> WatchedFragment()
            3 -> SettingsFragment()
            else -> SearchFragment()
        }
    }

    // 3
    override fun getCount(): Int {
        return 4
    }
}