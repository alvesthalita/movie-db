package com.thalita.movie_db_app.features.profile

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.beardedhen.androidbootstrap.BootstrapButton
import com.beardedhen.androidbootstrap.BootstrapCircleThumbnail
import com.beardedhen.androidbootstrap.BootstrapEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.thalita.movie_db_app.R
import com.thalita.movie_db_app.core.plataform.ConfigFirebase
import com.thalita.movie_db_app.core.plataform.LoadingProgressBar
import com.thalita.movie_db_app.core.plataform.UserAuth
import com.thalita.movie_db_app.core.plataform.ValidateInput
import com.thalita.movie_db_app.features.main.MainActivity
import com.thalita.movie_db_app.features.signin.SignUpUser

class ProfileFragment : Fragment() {

    private var databaseReference: DatabaseReference?=null
    private var auth: UserAuth?=null
    private var userProfile: SignUpUser?=null
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
    private var image_user: BootstrapCircleThumbnail?=null
    private val GALLERY = 1
    private val CAMERA = 2
    private val TAG = "PermissionDemo"
    private val CAMERA_REQUEST_CODE = 200
    private var pictureDialog: AlertDialog.Builder?=null

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
        image_user = view.findViewById(R.id.image_profile_user)

//        FirebaseApp.initializeApp(context!!)
        auth =UserAuth(activity!!)
        userProfile =SignUpUser()
        loadingProgressBar=
            LoadingProgressBar(view)
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

        image_user?.setOnClickListener {
            showPictureDialog()
        }
    }

    private fun logout(){
        activity?.let { UserAuth(it)
            .saveUser(null, null, false) }
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
        pictureDialog = AlertDialog.Builder(context!!)
        pictureDialog?.setTitle("Foto de perfil")
        val pictureDialogItems = arrayOf("Galeria", "Câmera", "Sair")
        pictureDialog?.setItems(pictureDialogItems) { _, which ->
            when (which) {
                0 -> choosePhotoFromGallery()
                1 -> takePhotoFromCamera()
                2 -> out()
            }
        }
        pictureDialog?.show()
    }

    private fun choosePhotoFromGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, GALLERY)
    }

    private fun takePhotoFromCamera() {
        if(setupPermissions()) {
            val intent=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, CAMERA)
        }else{
            makeRequest()
        }
    }

    private fun out(){
        pictureDialog = AlertDialog.Builder(context!!)
    }

    override fun onActivityResult(requestCode:Int, resultCode:Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == GALLERY) {
//            if (data != null) {
//                val contentURI = data.data
//                try {
//                    val bitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver, contentURI)
//                    val path = saveImage(bitmap)
//                    Toast.makeText(context, "Image Saved!", Toast.LENGTH_SHORT).show()
//                    image_user!!.setImageBitmap(bitmap)
//
//                } catch (e: IOException) {
//                    e.printStackTrace()
//                    Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show()
//                }
//            }
//        } else if (requestCode == CAMERA) {
//            val thumbnail = data!!.extras!!.get("data") as Bitmap
//            image_user!!.setImageBitmap(thumbnail)
//            saveImage(thumbnail)
//            Toast.makeText(context, "Image Saved!", Toast.LENGTH_SHORT).show()
//        }
    }

    private var bit: Bitmap?=null

//    private fun saveImage(myBitmap: Bitmap):String {
//        val bytes = ByteArrayOutputStream()
//        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
//        val wallpaperDirectory = File((Environment.getExternalStorageDirectory()).toString() + IMAGE_DIRECTORY)
//        // have the object build the directory structure, if needed.
//        Log.d("fee",wallpaperDirectory.toString())
//
//        if (!wallpaperDirectory.exists()) {
//            wallpaperDirectory.mkdirs()
//        }
//
//        try {
//            Log.d("heel",wallpaperDirectory.toString())
//            val f = File(wallpaperDirectory, ((Calendar.getInstance().timeInMillis).toString() + ".jpg"))
//            f.createNewFile()
//            val fo = FileOutputStream(f)
//            fo.write(bytes.toByteArray())
//            MediaScannerConnection.scanFile(context, arrayOf(f.path), arrayOf("image/jpeg"), null)
//            fo.close()
//            Log.d("TAG", "File Saved::--->" + f.absolutePath)
//
//            return f.absolutePath
//        } catch (e1: IOException) {
//            e1.printStackTrace()
//        }
//
//        return ""
//    }

    companion object {
        private const val IMAGE_DIRECTORY = "/movie-db"
    }

    private fun setupPermissions() : Boolean {
        val permission = ContextCompat.checkSelfPermission(context!!,
            Manifest.permission.CAMERA)

        return if (permission != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Permission to camera denied")
            makeRequest()
            false
        }else{
            true
        }
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(activity!!, arrayOf(Manifest.permission.CAMERA), CAMERA_REQUEST_CODE)
    }

}
