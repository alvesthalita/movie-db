package com.thalita.movie_db_app.ui.fragments

import android.Manifest.permission.CAMERA
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.beardedhen.androidbootstrap.BootstrapButton
import com.beardedhen.androidbootstrap.BootstrapEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
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

//        FirebaseApp.initializeApp(context!!)
        auth = UserAuth(activity!!)
        userProfile = User()
        loadingProgressBar=LoadingProgressBar(view)
        firebaseAuth = ConfigFirebase().getFirebaseAuth()
        databaseReference=FirebaseDatabase.getInstance().reference

        getUserProfile()
        initActions()
    }

    private fun initActions(){
        btnEdit?.setOnClickListener {
//            validateFields()
//            updateProfile()
            updateEmail()
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
//            updateNameAndPhoto()
            updateEmail()
        }

    }

    private fun getUserProfile() {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            // Name, email address, and profile photo Url
            val name = user.displayName
            val email = user.email
            val photoUrl = user.photoUrl

            // Check if user's email is verified
            val emailVerified = user.isEmailVerified

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            val uid = user.uid

            //Set the name on the fields
            tv_fullName?.text = name
            edt_email?.setText(email)
            loadingProgressBar!!.hidePogressBar()
            scrollView?.visibility = View.VISIBLE
        }
    }

    private fun updateNameAndPhoto() {
        val user = FirebaseAuth.getInstance().currentUser

        val profileUpdates=UserProfileChangeRequest.Builder()
            .setDisplayName(fullName)
//            .setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))
            .build()

        user!!.updateProfile(profileUpdates)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User profile updated.")
                }
            }
    }

    private fun updateEmail() {
        val user = FirebaseAuth.getInstance().currentUser

        user?.updateEmail(edt_email?.text.toString())
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User email address updated.")
                }
            }
    }

    private fun updatePassword() {
        // [START update_password]
        val user = FirebaseAuth.getInstance().currentUser
        val newPassword = "SOME-SECURE-PASSWORD"

        user?.updatePassword(newPassword)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User password updated.")
                }
            }
        // [END update_password]
    }

    private fun showPictureDialog() {
        val pictureDialog = AlertDialog.Builder(this)
        pictureDialog.setTitle("Foto de perfil")
        val pictureDialogItems = arrayOf("Galeria", "Câmera", "Sair")
        pictureDialog.setItems(pictureDialogItems
        ) { dialog, which ->
            when (which) {
                0 -> choosePhotoFromGallary()
                1 -> takePhotoFromCamera()
            }
        }
        pictureDialog.show()
    }

    fun choosePhotoFromGallary() {
        val galleryIntent = Intent(Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        startActivityForResult(galleryIntent, GALLERY)
    }

    private fun takePhotoFromCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA)
    }

    public override fun onActivityResult(requestCode:Int, resultCode:Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        /* if (resultCode == this.RESULT_CANCELED)
         {
         return
         }*/
        if (requestCode == GALLERY)
        {
            if (data != null)
            {
                val contentURI = data!!.data
                try
                {
                    val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, contentURI)
                    val path = saveImage(bitmap)
                    Toast.makeText(this@MainActivity, "Image Saved!", Toast.LENGTH_SHORT).show()
                    imageview!!.setImageBitmap(bitmap)

                }
                catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(this@MainActivity, "Failed!", Toast.LENGTH_SHORT).show()
                }

            }

        }
        else if (requestCode == CAMERA)
        {
            val thumbnail = data!!.extras!!.get("data") as Bitmap
            imageview!!.setImageBitmap(thumbnail)
            saveImage(thumbnail)
            Toast.makeText(this@MainActivity, "Image Saved!", Toast.LENGTH_SHORT).show()
        }
    }




}
