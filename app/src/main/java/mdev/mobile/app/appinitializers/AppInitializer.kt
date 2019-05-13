package mdev.mobile.app.appinitializers

import android.app.Application

fun appInitializer(application: Application) {
    val context = application.applicationContext
    // Itializers with fixed order
    val mandatoryinItializers = listOf(
        LogbackInitializer(context),
        LogStarterInitializer(),
        KoinInitializer(context)
    )
    mandatoryinItializers.forEach {
        it.init()
    }

    val initializer = listOf(
        LeakCanaryInitializer(application),
        StrictModeInitializer(),
        EpoxyInitializer(),
        CrashlyticsInitializer(context),
        AndroidThreeTenInitializer(application),
        StethoInitializer(application),
        RxJava2Initializer()
    )
    initializer.forEach {
        it.init()
    }
}
