package com.thalita.movie_db_app.features.movies

import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.thalita.movie_db_app.R
import com.thalita.movie_db_app.core.extension.hidePogressBar
import com.thalita.movie_db_app.core.extension.showProgressBar
import com.thalita.movie_db_app.core.plataform.BaseActivity
import com.thalita.movie_db_app.core.plataform.UserAuth
import java.util.*

/**
 * Em desenvolvimento
 */
class WatchedActivity : BaseActivity() {

    private var recyclerView: RecyclerView?=null
    private var databaseReference: DatabaseReference?=null
    private var favoritesList: MutableList<GetMovies>?=null
    private var auth: UserAuth?=null
    private var progressBar: ProgressBar?=null
    private var rootView: View?=null

    override fun setLayout() {
        hideTop(true)
        setContentView(R.layout.activity_watched)
    }

    override fun getObjects() {
        //To change body of created functions use File | Settings | File Templates.
    }

    override fun setObjects() {
        init()
    }

    private fun init(){
        recyclerView = findViewById(R.id.recycler_view)
        progressBar = findViewById(R.id.progressBar)
        favoritesList = mutableListOf()
        auth =UserAuth(this)
        rootView = window.decorView.rootView

        showProgressBar(rootView!!)
        populateMyList()
    }

    private fun populateMyList(){
        databaseReference=FirebaseDatabase.getInstance().reference

        databaseReference!!.child("favorites").orderByChild("email").equalTo(auth?.getEmail())
            .addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (snapshot in dataSnapshot.children) {
                        val isWatched= Objects.requireNonNull(snapshot.child("watchedMovie").value)

                        if(isWatched as Boolean) {
                            val day: GetMovies?=snapshot.getValue(GetMovies::class.java)
                            favoritesList?.add(day!!)
                        }
                    }

                    populateList()
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })

    }

    private fun populateList(){
        hidePogressBar(rootView!!)
        recyclerView?.layoutManager = GridLayoutManager(this, 3)
        val adapter =favoritesList?.let {
            MyListAdapter(
                this,
                it
            )
        }
        recyclerView!!.isClickable = true
        recyclerView?.adapter = adapter
    }
}
