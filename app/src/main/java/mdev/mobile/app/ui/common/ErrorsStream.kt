package mdev.mobile.app.ui.common

import io.reactivex.subjects.PublishSubject
import mdev.mobile.app.R
import mdev.mobile.base.kotlin.log.KLoggerT
import mdev.mobile.domain.error.LocalizedException
import mdev.mobile.domain.error.PermanentException

class ErrorsStream {
    private val errors = PublishSubject.create<Throwable>()

    fun create(log: KLoggerT) = errors.map {
        if (it !is LocalizedException) {
            PermanentException(R.string.error_unknown_error, cause = it)
        } else {
            it
        }
    }
        .doOnNext { log.warn(it.cause ?: it) { "Error stream:" } }

    fun sendError(error: Throwable) {
        errors.onNext(error)
    }
}
