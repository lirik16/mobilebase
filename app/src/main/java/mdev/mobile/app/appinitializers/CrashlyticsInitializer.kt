package mdev.mobile.app.appinitializers

import android.content.Context
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.core.CrashlyticsCore
import io.fabric.sdk.android.Fabric
import mdev.mobile.app.BuildConfig
import mdev.mobile.base.kotlin.util.Initializer

class CrashlyticsInitializer(private val context: Context) : Initializer {
    override fun init() {
        // Set up Crashlytics, disabled for debug builds
        val crashlyticsKit = Crashlytics.Builder()
            .core(CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build())
            .build()
        // Initialize Fabric with the debug-disabled crashlytics.
        Fabric.with(context, crashlyticsKit)
        Crashlytics.setString("build_time", BuildConfig.BUILD_TIME)
        Crashlytics.setString("commit_sha", BuildConfig.COMMIT_SHA)
        Crashlytics.setBool("is_ci_build", BuildConfig.IS_CI_BUILD)
    }
}
