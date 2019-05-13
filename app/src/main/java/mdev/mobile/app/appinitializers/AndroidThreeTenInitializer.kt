package mdev.mobile.app.appinitializers

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import mdev.mobile.base.kotlin.util.Initializer

class AndroidThreeTenInitializer(private val application: Application) : Initializer {
    override fun init() {
        AndroidThreeTen.init(application)
    }
}
