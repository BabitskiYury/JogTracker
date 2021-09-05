package com.example.yellow

import android.app.Application
import android.content.ContextWrapper
import com.pixplicity.easyprefs.library.Prefs
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class YellowApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initPrefs()
    }

    private fun initPrefs() {
        Prefs.Builder()
            .setContext(this)
            .setMode(ContextWrapper.MODE_PRIVATE)
            .setPrefsName(packageName)
            .setUseDefaultSharedPreference(true)
            .build()
    }
}
