package com.thalita.movie_db_app.features.signin

import android.content.Intent
import android.widget.Toast
import com.beardedhen.androidbootstrap.BootstrapButton
import com.beardedhen.androidbootstrap.BootstrapEditText
import com.google.firebase.auth.*
import com.google.firebase.database.DatabaseReference
import com.thalita.movie_db_app.R
import com.thalita.movie_db_app.core.extension.*
import com.thalita.movie_db_app.core.plataform.BaseActivity
import com.thalita.movie_db_app.core.plataform.ConfigFirebase
import com.thalita.movie_db_app.core.plataform.UserAuth
import com.thalita.movie_db_app.core.plataform.ValidateInput
import com.thalita.movie_db_app.features.main.MainActivity

class SignInActivity : BaseActivity() {

    private var edt_email: BootstrapEditText?=null
    private var edt_password: BootstrapEditText?=null
    private var edt_confirm_password: BootstrapEditText?=null
    private var edt_fullName: BootstrapEditText?=null
    private var btn_signIn: BootstrapButton?=null
    private var btn_cancel: BootstrapButton?=null
    private var firebaseAuth: FirebaseAuth?=null
    private var databaseReference: DatabaseReference?=null
    private var user: SignInUser?=null
    private var userAuth: UserAuth?=null

    override fun setLayout() {
        hideTop(true)
        setContentView(R.layout.activity_sign_in)
    }

    override fun getObjects() {
    }

    override fun setObjects() {
        init()
    }

    private fun init(){
        edt_fullName = findViewById(R.id.edt_signIn_fullName)
        edt_email = findViewById(R.id.edt_signIn_email)
        edt_password = findViewById(R.id.edt_signIn_password)
        edt_confirm_password = findViewById(R.id.edt_signIn_confirm_password)
        btn_signIn = findViewById(R.id.btn_signIn)
        btn_cancel = findViewById(R.id.btn_signIn_cancel)

        user =SignInUser()
        userAuth =UserAuth(this)
        firebaseAuth = ConfigFirebase().getFirebaseAuth()

        initActions()
    }

    private fun initActions(){
        btn_signIn?.setOnClickListener {
            validateFields()
        }

        btn_cancel?.setOnClickListener {
            finish()
        }
    }

    private fun validateFields(){
        if(edt_fullName!!.text.isEmpty()){
            showToastMessage(this, "O campo nome é obrigatório, tente novamente.")
            return
        }

        if(edt_email!!.text.isEmpty()){
            showToastMessage(this, "O campo de e-mail é obrigatório, tente novamente.")
            return
        }

        if(!ValidateInput().isEmailValid(edt_email!!.text.toString())){
            showToastMessage(this, "Por favor, informe um e-mail válida. Tente novamente.")
            return
        }

        if(edt_password!!.text.isEmpty()){
            showToastMessage(this, "O campo de senha é obrigatório, tente novamente.")
            return
        }

        if(edt_confirm_password!!.text.isEmpty()){
            showToastMessage(this, "O campo de confirmação de senha é obrigatório, tente novamente.")
            return
        }

        if(edt_password!!.text.toString() != edt_confirm_password!!.text.toString()){
            showToastMessage(this, "As senha não conferem, tente novamente")
            return
        }else{
            user!!.setFullName(edt_fullName!!.text.toString())
            user!!.setEmail(edt_email!!.text.toString())
            user!!.setPassword(edt_password!!.text.toString())
            signInNewUser()
        }

    }

    private fun signInNewUser(){
        val rootView = window.decorView.rootView
        btn_cancel?.invisible()
        btn_signIn?.invisible()
        showProgressBar(rootView!!)

        firebaseAuth!!.createUserWithEmailAndPassword(user!!.getEmail()!!, user!!.getPassword()!!)
            .addOnCompleteListener(
                this
            ) { task ->
                if (task.isSuccessful) {
                    setUserNameOnFirebase(user!!)
                    finish()
                    firebaseAuth!!.signOut()
                    openMain()
                } else {
                    var error=""
                    try {
                        throw task.exception!!
                    } catch (e: FirebaseAuthWeakPasswordException) {
                        error = "Digite uma senha mais forte, com letras e números"
                    } catch (e: FirebaseAuthInvalidCredentialsException) {
                        error = "E-mail inválido, digite um e-mail válido"
                    } catch (e: FirebaseAuthUserCollisionException) {
                        error = "Já existe este e-mail cadastrado."
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                    showToastMessage(this, error)
                }
            }

        btn_cancel?.visible()
        btn_signIn?.visible()
        hidePogressBar(rootView)
    }

    private fun setUserNameOnFirebase(user: SignInUser) {
        val userUpdate = FirebaseAuth.getInstance().currentUser

        val profileUpdates=UserProfileChangeRequest.Builder()
            .setDisplayName(edt_fullName?.text.toString())
            .build()

        userUpdate!!.updateProfile(profileUpdates)
            .addOnCompleteListener { task ->
                showToastMessage(this, "Cadastro efetuado com sucesso!!")
            }

        userAuth?.saveUser(user.getEmail(), user.getPassword())
    }

    private fun openMain() {
        firebaseAuth = ConfigFirebase().getFirebaseAuth()
        val userPreferences =
            UserAuth(this)
        firebaseAuth!!.signInWithEmailAndPassword(userPreferences.getEmail().toString(),
            userPreferences.getPassword().toString()
        )
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val intent=Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    showToastMessage(this, "Não foi possível completar a transação, tente mais tarde!!!")
                    firebaseAuth!!.signOut()
                    val intent=Intent(applicationContext, MainActivity::class.java)
                    finish()
                    startActivity(intent)
                }
            }
    }
}
