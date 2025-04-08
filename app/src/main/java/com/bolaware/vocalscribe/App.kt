package com.bolaware.vocalscribe

import android.app.Application
import com.bolaware.data.di.dataModule
import com.bolaware.feature_history.di.transcriptModule
import com.bolaware.feature_home.di.homeModule
import com.bolaware.speechrecognizer.di.speechRecognizerModule
import com.bolaware.vocalscribe.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class VocalScribeApp : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidContext(this@VocalScribeApp)
            modules(
                appModule,
                homeModule,
                dataModule,
                transcriptModule,
                speechRecognizerModule
            )
        }
    }
}
