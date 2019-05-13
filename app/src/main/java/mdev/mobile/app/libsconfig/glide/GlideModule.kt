package mdev.mobile.app.libsconfig.glide

import android.app.ActivityManager
import android.content.Context
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import mdev.mobile.app.appinitializers.GLIDE_OKHTTP_CLIENT
import mdev.mobile.app.util.common.DEBUG_MODE
import okhttp3.OkHttpClient
import org.koin.standalone.KoinComponent
import org.koin.standalone.get
import java.io.InputStream

@GlideModule
class GlideModule : AppGlideModule(), KoinComponent {
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        super.applyOptions(context, builder)

        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val options = RequestOptions().format(if (am.isLowRamDevice) DecodeFormat.PREFER_RGB_565 else DecodeFormat.PREFER_ARGB_8888)

        builder.setDefaultRequestOptions(options)
        if (DEBUG_MODE) {
            builder.setLogLevel(Log.VERBOSE)
        } else {
            builder.setLogLevel(Log.ERROR)
        }
    }

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        super.registerComponents(context, glide, registry)

        val okHttpClient: OkHttpClient = get(GLIDE_OKHTTP_CLIENT)
        val factory = OkHttpUrlLoader.Factory(okHttpClient)
        glide.registry.replace(GlideUrl::class.java, InputStream::class.java, factory)
    }

    override fun isManifestParsingEnabled() = false
}
