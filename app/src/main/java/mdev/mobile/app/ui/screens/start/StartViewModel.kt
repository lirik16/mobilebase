package mdev.mobile.app.ui.screens.start

import android.content.Context
import android.net.Uri
import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import kotlinx.coroutines.withContext
import mdev.mobile.app.ui.base.BaseViewModel
import mdev.mobile.app.ui.base.BaseViewState
import mdev.mobile.app.util.logger.createLogsZip
import mdev.mobile.base.kotlin.log.KLog
import mdev.mobile.base.kotlin.util.AppCoroutineDispatchers
import mdev.mobile.domain.error.LocalizedException
import org.koin.android.ext.android.get
import kotlin.reflect.KProperty1

private val log = KLog.logger { }

data class StartState(override val error: LocalizedException? = null) : BaseViewState()

class StartViewModel(
    initState: StartState,
    private val appCoroutineDispatchers: AppCoroutineDispatchers
) : BaseViewModel<StartState>(initState, log) {
    override fun getErrorProperty(): KProperty1<StartState, LocalizedException?> = StartState::error

    override fun StartState.copyWithError(localizedException: LocalizedException?): StartState = copy(error = localizedException)

    companion object : MvRxViewModelFactory<StartViewModel, StartState> {
        override fun create(viewModelContext: ViewModelContext, state: StartState): StartViewModel? {
            return with(viewModelContext.activity) {
                StartViewModel(state, get())
            }
        }
    }

    suspend fun getLogsUri(context: Context): Uri = withContext(appCoroutineDispatchers.io) { createLogsZip(context) }
}
