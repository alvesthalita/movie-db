package com.thalita.movie_db_app.core.plataform

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.thalita.movie_db_app.R

class ConfigFirebase {

    private var databaseReference: DatabaseReference? = null
    private var firebaseAuth: FirebaseAuth? = null
    private var firebaseStorage: FirebaseStorage? = null
    private var storageReference: StorageReference? = null
    private var firebaseRemoteConfig: FirebaseRemoteConfig? = null

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

    fun getFirebaseRemoteConfig(): FirebaseRemoteConfig? {
        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
        val configSettings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(3600)
            .build()
        firebaseRemoteConfig?.setConfigSettingsAsync(configSettings)
        firebaseRemoteConfig?.setDefaults(R.xml.remote_config_defaults)
        return firebaseRemoteConfig
    }
}