package com.thalita.movie_db_app.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.beardedhen.androidbootstrap.BootstrapButton
import com.beardedhen.androidbootstrap.BootstrapEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.thalita.movie_db_app.R
import com.thalita.movie_db_app.entities.User
import com.thalita.movie_db_app.ui.activities.MainActivity
import com.thalita.movie_db_app.utils.ConfigFirebase
import com.thalita.movie_db_app.utils.ValidateInput

class LoginFragment : Fragment() {

    private lateinit var edt_login : BootstrapEditText
    private lateinit var edt_password : BootstrapEditText
    private lateinit var btn_login : BootstrapButton
    private var databaseReference: DatabaseReference? = null
    private var firebaseAuth: FirebaseAuth? = null
    private var user: User? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val view: View = inflater.inflate(
            R.layout.fragment_login, container,
            false)

        initComponent(view)

        return view
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

    fun initComponent(view: View){

        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        edt_login = view.findViewById(R.id.edt_login_email)
        edt_password = view.findViewById(R.id.edt_login_password)
        btn_login = view.findViewById(R.id.btn_login)

        databaseReference = FirebaseDatabase.getInstance().reference
        firebaseAuth = ConfigFirebase().getFirebaseAuth()
        user = User()

        initActions()
    }

    private fun initActions(){
        btn_login.setOnClickListener {
            validateFields()
        }
    }

    private fun validateFields(){

        if(edt_login.text.isEmpty()){
            Toast.makeText(
                activity,
                "O campo de e-mail é obrigatório, tente novamente.",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        if(!ValidateInput().isEmailValid(edt_login.text.toString())){
            Toast.makeText(
                activity,
                "E-mail inválido, por favor insira um e-mail válido e tente novamente.",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        if(edt_password.text.isEmpty()){
            Toast.makeText(
                activity,
                "O campo de senha é obrigatório, tente novamente.",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        startLogin()
    }

    private fun startLogin() {
        user?.setEmail(edt_login.text.toString())
        user?.setSenha(edt_password.text.toString())

        user?.getEmail()?.let {
            firebaseAuth?.signInWithEmailAndPassword(it, user!!.getSenha()!!)
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            activity,
                            "Login efetuado com sucesso!!!",
                            Toast.LENGTH_LONG
                        ).show()
                        openHome()
                    } else {
                        Toast.makeText(
                            activity,
                            "Usuário ou senha inválidos",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }
    }

    private fun openHome(){
        val intent=Intent(activity, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.putExtra("userLogged", true)
        startActivity(intent)
    }
}
