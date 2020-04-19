package com.thalita.movie_db_app.features.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.beardedhen.androidbootstrap.BootstrapButton
import com.beardedhen.androidbootstrap.BootstrapEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.thalita.movie_db_app.R
import com.thalita.movie_db_app.core.extension.hidePogressBar
import com.thalita.movie_db_app.core.extension.invisible
import com.thalita.movie_db_app.core.extension.showProgressBar
import com.thalita.movie_db_app.core.extension.visible
import com.thalita.movie_db_app.features.signin.SignInUser
import com.thalita.movie_db_app.features.main.MainActivity
import com.thalita.movie_db_app.features.signin.SignInActivity
import com.thalita.movie_db_app.core.plataform.ConfigFirebase
import com.thalita.movie_db_app.core.plataform.UserAuth
import com.thalita.movie_db_app.core.plataform.ValidateInput

class LoginFragment : Fragment() {

    private lateinit var edt_login : BootstrapEditText
    private lateinit var edt_password : BootstrapEditText
    private lateinit var btn_login : BootstrapButton
    private lateinit var btn_signIn : BootstrapButton
    private lateinit var btn_recoverPassword : TextView
    private var databaseReference: DatabaseReference? = null
    private var firebaseAuth: FirebaseAuth? = null
    private var user: SignInUser? = null
    private var userAuth: UserAuth?=null
    private var rootView: View?=null
    private var buttonsLayout: LinearLayout?=null

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
        rootView = view
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        edt_login = view.findViewById(R.id.edt_login_email)
        edt_password = view.findViewById(R.id.edt_login_password)
        btn_login = view.findViewById(R.id.btn_login)
        btn_signIn = view.findViewById(R.id.btn_doSignin)
        btn_recoverPassword = view.findViewById(R.id.tv_password)
        buttonsLayout = view.findViewById(R.id.buttons_layout)

        databaseReference = FirebaseDatabase.getInstance().reference
        firebaseAuth = ConfigFirebase().getFirebaseAuth()
        user =SignInUser()
        userAuth = activity?.let {
            UserAuth(
                it
            )
        }

        initActions()
    }

    private fun initActions(){
        btn_login.setOnClickListener {
            validateFields()
        }

        btn_signIn.setOnClickListener {
            val intent = Intent(activity, SignInActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
        }

        btn_recoverPassword.setOnClickListener {
            val intent = Intent(activity, RecoverPasswordActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
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
        buttonsLayout?.invisible()
        showProgressBar(rootView!!)
        user?.setEmail(edt_login.text.toString())
        user?.setPassword(edt_password.text.toString())

        user?.getEmail()?.let {
            firebaseAuth?.signInWithEmailAndPassword(it, user!!.getPassword()!!)
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        userAuth?.saveUser(edt_login.text.toString(), null)
                        openHome()
                    } else {
                        buttonsLayout?.visible()
                        hidePogressBar(rootView!!)
                        Toast.makeText(
                            context,
                            "Usuário ou senha inválidos",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }
    }

    private fun openHome(){
        val intent=Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}
