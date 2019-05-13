package mdev.mobile.app.appinitializers

import android.os.StrictMode
import mdev.mobile.app.util.common.DEVELOPER_MODE
import mdev.mobile.base.kotlin.util.Initializer

class StrictModeInitializer : Initializer {
    override fun init() {
        if (DEVELOPER_MODE) {
            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build()
            )

            StrictMode.setVmPolicy(
                StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build()
            )
        }
    }
}
