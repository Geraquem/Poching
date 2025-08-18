package com.mmfsin.pochounter.base

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
//        Realm.init(this)
//        MobileAds.initialize(this) {}

//        getFCMToken()
    }

//    private fun getFCMToken() {
//        FirebaseMessaging.getInstance().token.addOnCompleteListener {
//            if (it.isSuccessful) Log.i("FCM", it.result)
//            else Log.i("FCM", "no token")
//        }
//    }
}