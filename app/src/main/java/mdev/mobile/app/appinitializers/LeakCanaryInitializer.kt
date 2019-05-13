package mdev.mobile.app.appinitializers

import android.app.Application
import com.squareup.leakcanary.LeakCanary
import mdev.mobile.base.kotlin.util.Initializer

// TODO: Update to version 2.0.0
class LeakCanaryInitializer(private val application: Application) : Initializer {
    override fun init() {
        if (LeakCanary.isInAnalyzerProcess(application)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        LeakCanary.install(application)
    }
}
