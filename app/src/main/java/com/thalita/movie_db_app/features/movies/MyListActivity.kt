package com.thalita.movie_db_app.features.movies

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.thalita.movie_db_app.R
import com.thalita.movie_db_app.core.plataform.BaseActivity
import com.thalita.movie_db_app.core.plataform.UserAuth


class MyListActivity : BaseActivity() {

    private var recyclerView: RecyclerView?=null
    private var databaseReference: DatabaseReference?=null
    private var favoritesList: MutableList<FavoriteMovie>?=null
    private var favoriteMovie: FavoriteMovie?=null
    private var auth: UserAuth?=null
    private lateinit var movieList: MutableList<FavoriteMovie>

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
        favoritesList = mutableListOf()
        auth =UserAuth(this)
        populateMyList()
    }

    private fun populateMyList(){
        movieList = ArrayList()
        databaseReference=FirebaseDatabase.getInstance().reference

        databaseReference!!.child("favorites").orderByChild("email").equalTo(auth?.getEmail())
            .addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (snapshot in dataSnapshot.children) {
                        favoriteMovie = snapshot.getValue(FavoriteMovie::class.java)
                        favoriteMovie?.let { movieList.add(it) }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {}
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

    //                        val result: FavoriteMovie?= dataSnapshot.getValue(FavoriteMovie::class.java)
////                        val `object`=dataSnapshot.getValue(Any::class.java)
////                        val json=Gson().toJson(`object`)
////                        val movies: FavoriteMovieResult?= dataSnapshot.getValue(FavoriteMovieResult::class.java)
////

}
