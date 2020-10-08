package com.thalita.movie_db_app.features.login

import com.beardedhen.androidbootstrap.BootstrapButton
import com.beardedhen.androidbootstrap.BootstrapEditText
import com.google.firebase.auth.FirebaseAuth
import com.thalita.movie_db_app.R
import com.thalita.movie_db_app.core.extension.invisible
import com.thalita.movie_db_app.core.extension.showProgressBar
import com.thalita.movie_db_app.core.extension.showToastMessage
import com.thalita.movie_db_app.core.plataform.BaseActivity
import com.thalita.movie_db_app.core.plataform.ConfigFirebase
import com.thalita.movie_db_app.core.plataform.ValidateInput

class RecoverPasswordActivity : BaseActivity() {

    private var btn_cancel: BootstrapButton?=null
    private var btn_send: BootstrapButton?=null
    private var edt_recover_email: BootstrapEditText?=null
    private var firebaseAuth: FirebaseAuth?=null

    override fun setLayout() {
        hideTop(true)
        setContentView(R.layout.activity_recover_password)
    }

    override fun getObjects() {
    }

    override fun setObjects() {
        init()

    }

    private fun init(){
        btn_cancel = findViewById(R.id.btn_recover_cancel)
        btn_send = findViewById(R.id.btn_recover_sendEmail)
        edt_recover_email = findViewById(R.id.edt_recover_email)

        firebaseAuth = ConfigFirebase().getFirebaseAuth()

        initActions()
    }

    private fun initActions(){
        btn_send?.setOnClickListener {
            validateField()
        }

        btn_cancel?.setOnClickListener {
            finish()
        }
    }

    private fun validateField(){
        if(edt_recover_email!!.text.isEmpty()){
            showToastMessage(this, "O campo de e-mail é obrigatório, tente novamente.")
            return
        }

        if(!ValidateInput().isEmailValid(edt_recover_email!!.text.toString())){
            showToastMessage(this, "Por favor, informe um e-mail válida. Tente novamente.")
            return
        }else{
            val rootView = window.decorView.rootView
            btn_cancel?.invisible()
            btn_send?.invisible()
            showProgressBar(rootView!!)
            recoverPassword()
        }
    }

    private fun recoverPassword(){
        if(!edt_recover_email!!.text.isEmpty()) {
            val email = edt_recover_email!!.text.toString()

            firebaseAuth!!.sendPasswordResetEmail(email).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    showToastMessage(this, "Em instantes, você receberá um e-mail para a recuperação de senha")
                    val intent=intent
                    finish()
                    startActivity(intent)
                } else {
                    showToastMessage(this, "Não foi possível recuperar sua senha, tente novamente mais tarde.")
                    val intent=intent
                    finish()
                    startActivity(intent)
                }
            }
        }
    }

}
