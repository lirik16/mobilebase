package mdev.mobile.app.appinitializers

import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins
import mdev.mobile.base.kotlin.log.KLog
import mdev.mobile.base.kotlin.util.Initializer

private val log = KLog.logger { }

class RxJava2Initializer : Initializer {
    override fun init() {
        RxJavaPlugins.setErrorHandler { error ->
            if (error is UndeliverableException) {
                log.warn(error.cause) { "Undeliverable exception received" }
            } else if ((error is NullPointerException) || (error is IllegalArgumentException)) {
                // that's likely a bug in the application
                Thread.currentThread().uncaughtExceptionHandler.uncaughtException(Thread.currentThread(), error)
            } else if (error is IllegalStateException) {
                // that's a bug in RxJava or in a custom operator
                Thread.currentThread().uncaughtExceptionHandler.uncaughtException(Thread.currentThread(), error)
            } else {
                log.warn(error) { "Undeliverable exception received" }
            }
        }
    }
}
