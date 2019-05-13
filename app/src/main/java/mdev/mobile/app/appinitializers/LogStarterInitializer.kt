package mdev.mobile.app.appinitializers

import mdev.mobile.app.BuildConfig
import mdev.mobile.base.kotlin.log.KLog
import mdev.mobile.base.kotlin.util.Initializer
import java.text.DateFormat
import java.util.Date

private val log = KLog.logger { }

class LogStarterInitializer : Initializer {
    override fun init() {
        log.info {
            "\n\n\n------------------------\nStarter: " +
                    "localDateTime = ${DateFormat.getDateTimeInstance().format(Date())}, " +
                    "buildTime = ${BuildConfig.BUILD_TIME}, " +
                    "commitSha = ${BuildConfig.COMMIT_SHA}, " +
                    "versionCode = ${BuildConfig.VERSION_CODE}, " +
                    "versionName = ${BuildConfig.VERSION_NAME}, " +
                    "buildType = ${BuildConfig.BUILD_TYPE}"
        }
    }
}
