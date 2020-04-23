package com.thalita.movie_db_app.core.plataform

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.remoteconfig.FirebaseRemoteConfig

class FeatureFlaggingDefault : FeatureFlaggingInterface {

    private var remoteConfig: FirebaseRemoteConfig?=null

    init {
        remoteConfig = ConfigFirebase().getFirebaseRemoteConfig()

        remoteConfig?.fetch()?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // RemoteConfig fetch must be activated
                val updatedFlag = task.result
                remoteConfig?.fetchAndActivate()
                Log.d(TAG, "RemoteConfig fetch successful!")
            } else {
                Log.d(TAG, "RemoteConfig fetch failed!")
            }
        }
    }

    override val showStatistics: Boolean
        get()=remoteConfig?.getBoolean("show_statistics")!!


}