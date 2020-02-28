package com.thalita.movie_db_app.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.beardedhen.androidbootstrap.BootstrapButton
import com.google.firebase.auth.FirebaseAuth
import com.thalita.movie_db_app.R
import com.thalita.movie_db_app.ui.activities.MainActivity
import com.thalita.movie_db_app.ui.activities.SignInActivity
import com.thalita.movie_db_app.utils.ConfigFirebase
import com.thalita.movie_db_app.utils.UserAuth

class SettingsFragment : Fragment() {

    private var btn_logout: BootstrapButton?=null
    private var firebaseAuth: FirebaseAuth?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val view: View = inflater.inflate(
            R.layout.fragment_settings, container,
            false)

        init(view)
        return view
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

    private fun init(view: View){
        btn_logout = view.findViewById(R.id.btn_settings_logout)
        firebaseAuth = ConfigFirebase().getFirebaseAuth()

        btn_logout?.setOnClickListener {
            logout()
        }
    }

    private fun logout(){
        activity?.let { UserAuth(it).saveUser(null, null, false) }
        firebaseAuth!!.signOut()
        context?.let { UserAuth(it).logout() }
        val intent=Intent(activity, MainActivity::class.java)
        startActivity(intent)
    }
}
