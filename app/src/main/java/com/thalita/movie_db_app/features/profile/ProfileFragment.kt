package com.thalita.movie_db_app.features.profile

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.beardedhen.androidbootstrap.BootstrapButton
import com.beardedhen.androidbootstrap.BootstrapCircleThumbnail
import com.beardedhen.androidbootstrap.BootstrapEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.thalita.movie_db_app.core.extension.hidePogressBar
import com.thalita.movie_db_app.core.plataform.ConfigFirebase
import com.thalita.movie_db_app.core.plataform.UserAuth
import com.thalita.movie_db_app.core.plataform.ValidateInput
import com.thalita.movie_db_app.features.main.MainActivity
import com.thalita.movie_db_app.features.signin.SignInUser
import com.thalita.movie_db_app.R
import com.thalita.movie_db_app.core.extension.invisible
import com.thalita.movie_db_app.core.extension.showProgressBar
import com.thalita.movie_db_app.core.extension.visible


/**
 * Em desenvolvimento
 */
class ProfileFragment : Fragment() {

    private var databaseReference: DatabaseReference?=null
    private var auth: UserAuth?=null
    private var userProfile: SignInUser?=null
    private var tv_fullName: TextView?=null
    private var edt_email: BootstrapEditText?=null
    private var edt_password: BootstrapEditText?=null
    private var edt_confirmPassword: BootstrapEditText?=null
    private var scrollView: ScrollView?=null
    private var btnEdit: BootstrapButton?=null
    private var btnLogOut: BootstrapButton?=null
    private var firebaseAuth: FirebaseAuth?=null
    private var image_user: BootstrapCircleThumbnail?=null
    private var edit_photo: TextView?=null
    private var rootView: View?=null

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
        rootView = view
        tv_fullName = view.findViewById(R.id.tv_profile_fullName)
        edt_email = view.findViewById(R.id.edt_profile_email)
        edt_password = view.findViewById(R.id.edt_profile_password)
        edt_confirmPassword = view.findViewById(R.id.edt_profile_confirm_password)
        scrollView = view.findViewById(R.id.scrollView)
        btnEdit = view.findViewById(R.id.btn_profile_edit)
        btnLogOut = view.findViewById(R.id.btn_profile_logout)
        image_user = view.findViewById(R.id.image_profile_user)
        edit_photo = view.findViewById(R.id.tv_edit_photo)
        auth =UserAuth(activity!!)
        userProfile =SignInUser()
        firebaseAuth = ConfigFirebase().getFirebaseAuth()
        databaseReference=FirebaseDatabase.getInstance().reference

        getUserProfile()
        initActions()
    }

    private fun initActions(){
        underlineText()

        btnEdit?.setOnClickListener {
            btnEdit?.invisible()
            btnLogOut?.invisible()
            showProgressBar(rootView!!)
            validateFields()
        }

        btnLogOut?.setOnClickListener {
            logout()
        }
    }

    private fun underlineText() {
        val text= "<u><b>Editar Foto</b></u>"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            edit_photo?.text=Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY)
        }
    }

    private fun logout(){
        activity?.let { UserAuth(it)
            .saveUser(null, null) }
        firebaseAuth!!.signOut()
        context?.let { UserAuth(it).logout() }
        val intent=Intent(activity, MainActivity::class.java)
        startActivity(intent)
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
            updateEmail()
        }

    }

    private fun getUserProfile() {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            // Name, email address, and profile photo Url
            val name = user.displayName
            val email = user.email

            tv_fullName?.text = name
            edt_email?.setText(email)
            hidePogressBar(rootView!!)
            scrollView?.visible()
        }
    }

    private fun updateEmail() {
        val user = FirebaseAuth.getInstance().currentUser
        user?.updateEmail(edt_email?.text.toString())
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    updatePassword()
                    Log.d(TAG, "User email address updated.")
                }
            }

    }

    private fun updatePassword() {
        val user = FirebaseAuth.getInstance().currentUser
        val newPassword = edt_password?.text.toString()

        user?.updatePassword(newPassword)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User password updated.")
                    updateEmailOnFavoritesTable(edt_email?.text.toString())
                    btnEdit?.visible()
                    btnLogOut?.visible()
                    hidePogressBar(rootView!!)
                    edt_password?.setText("")
                    edt_confirmPassword?.setText("")

                    Toast.makeText(
                        context,
                        "Dados alterados com sucesso!!",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }

    /**
     * Faz o update do email na tabela de favoritos
     */
    private fun updateEmailOnFavoritesTable(email: String){
        databaseReference = ConfigFirebase().getFirebase()
        databaseReference?.child("favorites")?.child("email")?.setValue(email);
        auth?.saveUser(email, null)
    }

}
