package mdev.mobile.app.util.logger

import mdev.mobile.base.kotlin.log.KLog
import okhttp3.logging.HttpLoggingInterceptor

private val log = KLog.logger { }

class RetrofitLogger : HttpLoggingInterceptor.Logger {
    override fun log(message: String) {
        log.info { message }
    }
}
