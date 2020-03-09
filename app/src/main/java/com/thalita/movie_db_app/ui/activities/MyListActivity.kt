package com.thalita.movie_db_app.ui.activities

import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.google.gson.Gson
import com.thalita.movie_db_app.R
import com.thalita.movie_db_app.entities.FavoriteMovie
import com.thalita.movie_db_app.ui.activities.generic.GenericActivity
import com.thalita.movie_db_app.ui.adapters.MyListAdapter
import com.thalita.movie_db_app.utils.UserAuth


class MyListActivity : GenericActivity() {

    private var recyclerView: RecyclerView?=null
    private var databaseReference: DatabaseReference?=null
    private var favoritesList: MutableList<FavoriteMovie>?=null
    private var favorite: FavoriteMovie?=null
    private var auth: UserAuth?=null

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
        auth = UserAuth(this)
        favorite = FavoriteMovie()

        populateMyList()
    }

    private fun populateMyList(){
        databaseReference=FirebaseDatabase.getInstance().reference

        databaseReference!!.child("favorites").orderByChild("email").equalTo(auth?.getEmail())
            .addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    Log.d("id", "sim")

                    for (snapshot in dataSnapshot.children) {
//                        favorite = snapshot.getValue(FavoriteMovie::class.java)
                        val `object`=dataSnapshot.getValue(Any::class.java)
                        val json=Gson().toJson(`object`)
                        val example: FavoriteMovie=Gson().fromJson(json, FavoriteMovie::class.java)
                        Log.e("json", "json: " + example.getTitleMovie())
                    }

//                    for (snapshot in dataSnapshot.children) {
//                        favorite = snapshot.getValue(FavoriteMovie::class.java)
//                        favorite?.let { favoritesList!!.add(it) }
//                    }

                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })
    }

    private fun populateList(){
        recyclerView?.layoutManager = GridLayoutManager(this, 3)
        val adapter =favoritesList?.let { MyListAdapter(this, it) }
        recyclerView!!.isClickable = true
        recyclerView?.adapter = adapter
    }

}
