package mdev.mobile.app.ui.base

import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.Success
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import mdev.mobile.app.ui.common.ErrorsStream
import mdev.mobile.app.util.common.DEBUG_MODE
import mdev.mobile.base.kotlin.log.KLoggerT
import mdev.mobile.domain.error.LocalizedException
import kotlin.coroutines.CoroutineContext
import kotlin.reflect.KProperty1

abstract class BaseViewModel<S : BaseViewState>(
    initialState: S,
    log: KLoggerT
) : BaseMvRxViewModel<S>(initialState, DEBUG_MODE), CoroutineScope {
    protected val errors = ErrorsStream()
    private val disposables = CompositeDisposable()
    override val coroutineContext: CoroutineContext = createMainScopeWithErrorsStream(errors)

    init {
        errors.create(log).execute {
            if (it is Success) {
                copyWithError(it())
            } else {
                this@execute
            }
        }
    }

    fun clearError(error: LocalizedException?) {
        setState { if (error === this@setState.error) copyWithError(null) else this@setState }
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
        cancel()
    }

    abstract fun getErrorProperty(): KProperty1<S, LocalizedException?>

    protected abstract fun S.copyWithError(localizedException: LocalizedException?): S

    protected fun handleError(error: Throwable) {
        errors.sendError(error)
    }
}

private fun createMainScopeWithErrorsStream(errorsStream: ErrorsStream): CoroutineContext {
    return SupervisorJob() + Dispatchers.Main + CoroutineExceptionHandler { _, throwable ->
        errorsStream.sendError(throwable)
    }
}
