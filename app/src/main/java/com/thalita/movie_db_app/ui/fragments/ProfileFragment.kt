package com.thalita.movie_db_app.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.beardedhen.androidbootstrap.BootstrapButton
import com.beardedhen.androidbootstrap.BootstrapEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.thalita.movie_db_app.R
import com.thalita.movie_db_app.entities.User
import com.thalita.movie_db_app.ui.activities.MainActivity
import com.thalita.movie_db_app.utils.ConfigFirebase
import com.thalita.movie_db_app.utils.LoadingProgressBar
import com.thalita.movie_db_app.utils.UserAuth
import com.thalita.movie_db_app.utils.ValidateInput

class ProfileFragment : Fragment() {

    private var databaseReference: DatabaseReference?=null
    private var auth: UserAuth?=null
    private var userProfile: User?=null
    private var tv_fullName: TextView?=null
    private var edt_email: BootstrapEditText?=null
    private var edt_password: BootstrapEditText?=null
    private var edt_confirmPassword: BootstrapEditText?=null
    private var scrollView: ScrollView?=null
    private var loadingProgressBar: LoadingProgressBar?=null
    private var btnEdit: BootstrapButton?=null
    private var btnLogOut: BootstrapButton?=null
    private var firebaseAuth: FirebaseAuth?=null
    private var key: String?=null
    private var fullName: String?=null

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view: View=inflater.inflate(R.layout.fragment_profile, container, false)
        init(view)
        return view
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

    private fun init(view: View) {
        tv_fullName = view.findViewById(R.id.tv_profile_fullName)
        edt_email = view.findViewById(R.id.edt_profile_email)
        edt_password = view.findViewById(R.id.edt_profile_password)
        edt_confirmPassword = view.findViewById(R.id.edt_profile_confirm_password)
        scrollView = view.findViewById(R.id.scrollView)
        btnEdit = view.findViewById(R.id.btn_profile_edit)
        btnLogOut = view.findViewById(R.id.btn_profile_logout)

        auth = UserAuth(activity!!)
        userProfile = User()
        loadingProgressBar=LoadingProgressBar(view)
        firebaseAuth = ConfigFirebase().getFirebaseAuth()
        databaseReference=FirebaseDatabase.getInstance().reference

        loadProfile()
        initActions()
    }

    private fun initActions(){
        btnEdit?.setOnClickListener {
//            validateFields()
            updateProfile()
        }

        btnLogOut?.setOnClickListener {
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

    private fun loadProfile(){
        loadingProgressBar!!.showProgressBar()

        databaseReference!!.child("users").orderByChild("email").equalTo(auth?.getEmail())
            .addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (snapshot in dataSnapshot.children) {
                        userProfile = snapshot.getValue(User::class.java)
                        key = snapshot.key.toString()
                        fullName = userProfile?.getFullName()

                        tv_fullName?.text = fullName
                        edt_email?.setText(userProfile?.getEmail())
                        loadingProgressBar!!.hidePogressBar()
                        scrollView?.visibility = View.VISIBLE
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })
    }

    private fun validateFields(){
        if(edt_email!!.text.isEmpty()){
            Toast.makeText(
                activity,
                "O campo de e-mail é obrigatório, tente novamente.",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        if(!ValidateInput().isEmailValid(edt_email!!.text.toString())){
            Toast.makeText(
                activity,
                "Por favor, informe um e-mail válida. Tente novamente.",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        if(edt_password!!.text.isEmpty()){
            Toast.makeText(
                activity,
                "O campo de senha é obrigatório, tente novamente.",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        if(edt_confirmPassword!!.text.isEmpty()){
            Toast.makeText(
                activity,
                "O campo de confirmação de senha é obrigatório, tente novamente.",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        if(edt_password!!.text.toString() != edt_confirmPassword!!.text.toString()){
            Toast.makeText(
                activity,
                "As senha não conferem, tente novamente",
                Toast.LENGTH_LONG
            ).show()
            return
        }else{
            updateProfile()
        }

    }

    private fun updateProfile(){
        userProfile = User()
        userProfile?.setEmail(edt_email?.text.toString())
        userProfile?.setPassword(edt_password?.text.toString())
        userProfile?.setFullName(fullName!!)
        auth?.saveUser(edt_email?.text.toString(), null, true)
        

        databaseReference?.child("users")?.child(key!!)?.setValue(userProfile)?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(
                    context,
                    "Dados atualizados com sucesso!!",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(
                    context,
                    "Não foi possível alterar os dados, tente novamente mais tarde.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

}
