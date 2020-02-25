package com.thalita.movie_db_app.ui.activities

import com.beardedhen.androidbootstrap.BootstrapButton
import com.thalita.movie_db_app.R
import com.thalita.movie_db_app.ui.activities.generic.GenericActivity

class RecoverPasswordActivity : GenericActivity() {

    private var btn_cancel: BootstrapButton?=null

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

        initActions()
    }

    private fun initActions(){
        btn_cancel?.setOnClickListener {
            finish()
        }
    }

}
