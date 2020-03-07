package com.thalita.movie_db_app.ui.activities

import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.thalita.movie_db_app.R
import com.thalita.movie_db_app.entities.FavoriteMovie
import com.thalita.movie_db_app.ui.adapters.MovieListAdapter
import com.thalita.movie_db_app.ui.adapters.MyListAdapter

class MyListActivity : AppCompatActivity() {

    private var recyclerView: RecyclerView?=null
    private var databaseReference: DatabaseReference?=null
    private var favoritesList: MutableList<FavoriteMovie>?=null
    private var favorite: FavoriteMovie?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_list)

        init()
    }

    private fun init(){
        recyclerView = findViewById(R.id.recycler_view)
        favoritesList = mutableListOf<FavoriteMovie>()

        populateMyList()
    }

    private fun populateMyList(){
        databaseReference=FirebaseDatabase.getInstance().reference
        databaseReference!!.child("favorites").orderByChild("email").equalTo("thalita@gmail.com")
            .addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    Log.d("id", "sim")

                    for (snapshot in dataSnapshot.children) {
                        favorite = snapshot.getValue(FavoriteMovie::class.java)
                        favorite?.let { favoritesList!!.add(it) }
                    }

                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })

        populateList()
    }

    private fun populateList(){
        recyclerView?.layoutManager = GridLayoutManager(this, 3)
        val adapter =favoritesList?.let { MyListAdapter(this, it) }
        recyclerView!!.isClickable = true
        recyclerView?.adapter = adapter
    }

}
