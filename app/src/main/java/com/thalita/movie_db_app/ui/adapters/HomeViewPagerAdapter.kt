package com.thalita.movie_db_app.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.thalita.movie_db_app.ui.fragments.MyListFragment
import com.thalita.movie_db_app.ui.fragments.HomeFragment
import com.thalita.movie_db_app.ui.fragments.StatisticFragment

@Suppress("DEPRECATION")
class HomeViewPagerAdapter(fragmentManager: FragmentManager, private var viewPager: ViewPager) :
    FragmentStatePagerAdapter(fragmentManager) {

    // 2
    override fun getItem(position: Int): Fragment {
//        viewPager.addOnPageChangeListener(pageChangeListener)

        return when (position) {
            0 -> HomeFragment()
            1 -> MyListFragment()
            2 -> StatisticFragment()
//            3 -> SettingsFragment()
            else -> HomeFragment()
        }
    }

    // 3
    override fun getCount(): Int {
        return 4
    }
}