package mdev.mobile.app.ui.screens.splash

import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import kotlinx.coroutines.delay
import mdev.mobile.app.ui.base.BaseViewModel
import mdev.mobile.app.ui.base.BaseViewState
import mdev.mobile.base.kotlin.log.KLog
import mdev.mobile.domain.error.LocalizedException
import java.util.concurrent.TimeUnit
import kotlin.reflect.KProperty1

private val log = KLog.logger { }

data class SplashState(override val error: LocalizedException? = null) : BaseViewState()

class SplashViewModel(
    initState: SplashState
) : BaseViewModel<SplashState>(initState, log) {
    private val creationTime: Long = System.currentTimeMillis()

    override fun getErrorProperty(): KProperty1<SplashState, LocalizedException?> = SplashState::error
    override fun SplashState.copyWithError(localizedException: LocalizedException?) = copy(error = localizedException)

    companion object : MvRxViewModelFactory<SplashViewModel, SplashState> {
        private val MIN_SHOW_MILLIS = TimeUnit.SECONDS.toMillis(1)

        override fun create(viewModelContext: ViewModelContext, state: SplashState): SplashViewModel? {
            return with(viewModelContext.activity) {
                SplashViewModel(state)
            }
        }
    }

    suspend fun splashScreenDelay() {
        val delayTime = (creationTime + MIN_SHOW_MILLIS) - System.currentTimeMillis()
        if (delayTime > 0) {
            delay(delayTime)
        }
    }
}
