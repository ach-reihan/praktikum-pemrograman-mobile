package com.openingtablexml

import android.app.Application
import timber.log.Timber

class OpeningApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        Timber.d("Application onCreate: Timber berhasil diinisialisasi")
    }
}