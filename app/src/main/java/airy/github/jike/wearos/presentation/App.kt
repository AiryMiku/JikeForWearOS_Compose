package airy.github.jike.wearos.presentation

import android.app.Application
import android.content.Context
import kotlin.properties.Delegates

class App: Application() {
    companion object {
        private var instance: App by Delegates.notNull()

        fun getAppContext(): Context = instance
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}