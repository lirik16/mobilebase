package mdev.mobile.app.appinitializers

import android.content.Context
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import mdev.mobile.app.util.common.DEBUG_MODE
import mdev.mobile.base.kotlin.util.Initializer
import okhttp3.OkHttpClient

// We have separate version of this file for debug, staging and release build types
class StethoInitializer(private val context: Context) : Initializer {
    companion object {
        fun addStethoInterceptor(builder: OkHttpClient.Builder) {
            if (DEBUG_MODE) {
                builder.addNetworkInterceptor(StethoInterceptor())
            }
        }
    }

    override fun init() {
        if (DEBUG_MODE) {
            Stetho.initializeWithDefaults(context)
        }
    }
}
