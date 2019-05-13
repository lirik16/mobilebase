package mdev.mobile.app.appinitializers

import android.content.Context
import mdev.mobile.base.kotlin.util.Initializer
import okhttp3.OkHttpClient

// We have separate version of this file for debug, staging and release build types
class StethoInitializer(private val context: Context) : Initializer {
    companion object {
        fun addStethoInterceptor(builder: OkHttpClient.Builder) {
        }
    }

    override fun init() {
    }
}
