package com.thalita.movie_db_app.features.movies

import android.util.Log
import android.widget.ProgressBar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.thalita.movie_db_app.R
import com.thalita.movie_db_app.core.extension.showProgressBar
import com.thalita.movie_db_app.core.plataform.BaseActivity
import com.thalita.movie_db_app.core.plataform.UserAuth

/**
 * Em desenvolvimento
 */
class MyListActivity : BaseActivity() {

    private var recyclerView: RecyclerView?=null
    private var databaseReference: DatabaseReference?=null
    private var favoritesList: MutableList<GetMovies>?=null
    private var auth: UserAuth?=null
    private var progressBar: ProgressBar?=null

    override fun setLayout() {
        hideTop(true)
        setContentView(R.layout.activity_my_list)
    }

    override fun getObjects() {
    }


    override fun setObjects() {
        init()
    }

    private fun init(){
        recyclerView = findViewById(R.id.recycler_view)
        progressBar = findViewById(R.id.progressBar)
        favoritesList = mutableListOf()
        auth =UserAuth(this)
        val rootView = window.decorView.rootView

        showProgressBar(rootView)
        populateMyList()
    }

    private fun populateMyList(){
        databaseReference=FirebaseDatabase.getInstance().reference
        databaseReference!!.child("favorites").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (snapshot in dataSnapshot.children) {
                        val movies: GetMovies? = snapshot.getValue(GetMovies::class.java)
                        favoritesList?.add(movies!!)
                    }

                    populateList()
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.d("MyListActivity: ", databaseError.message)
                }
            })

    }

    private fun populateList(){
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
