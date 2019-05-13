package mdev.mobile.app

import android.app.Application
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import mdev.mobile.app.appinitializers.appInitializer

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        GlobalScope.launch(Dispatchers.IO) {
            appInitializer(this@App)
        }
    }
}
