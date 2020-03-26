package com.thalita.movie_db_app.core.plataform

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class ConfigFirebase {

    private var databaseReference: DatabaseReference? = null
    private var firebaseAuth: FirebaseAuth? = null
    private var firebaseStorage: FirebaseStorage? = null
    private var storageReference: StorageReference? = null

    fun getFirebase(): DatabaseReference? {
        if (databaseReference == null) {
            databaseReference = FirebaseDatabase.getInstance().reference
        }
        return databaseReference
    }

    fun getFirebaseAuth(): FirebaseAuth? {
        if (firebaseAuth == null) {
            firebaseAuth = FirebaseAuth.getInstance()
        }
        return firebaseAuth
    }

    fun getFirebaseStorage(): FirebaseStorage? {
        if (firebaseStorage == null) {
            firebaseStorage = FirebaseStorage.getInstance()
        }
        return firebaseStorage
    }

    fun getFirebaseStorageReference(): StorageReference? {
        if (storageReference == null) {
            storageReference = FirebaseStorage.getInstance().reference
        }
        return storageReference
    }
}