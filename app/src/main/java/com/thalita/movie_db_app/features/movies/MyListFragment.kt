package com.thalita.movie_db_app.features.movies

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.thalita.movie_db_app.R


class MyListFragment : Fragment() {

    private var linearMyList: LinearLayout?=null
    private var linearWatched: LinearLayout?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val view: View = inflater.inflate(
            R.layout.fragment_my_list, container,
            false)

        init(view)
        return view
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

    private fun init(view: View){
        linearMyList = view.findViewById(R.id.linear_myList)
        linearWatched = view.findViewById(R.id.linear_watched)

        initActions()
    }

    private fun initActions(){
        linearMyList?.setOnClickListener {
            startActivity(MyListActivity::class.java, null)
        }

        linearWatched?.setOnClickListener {
            startActivity(WatchedActivity::class.java, null)
        }
    }

    fun startActivity(activityType: Class<*>, bundle: Bundle?) {
        val intent = Intent(context, activityType)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP

        if (bundle != null) {
            intent.putExtras(bundle)
        }

        startActivity(intent)
    }
}
