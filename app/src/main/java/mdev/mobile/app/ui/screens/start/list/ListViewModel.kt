package mdev.mobile.app.ui.screens.start.list

import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import mdev.mobile.app.ui.base.BaseViewModel
import mdev.mobile.app.ui.base.BaseViewState
import mdev.mobile.base.kotlin.log.KLog
import mdev.mobile.base.kotlin.util.AppCoroutineDispatchers
import mdev.mobile.domain.error.LocalizedException
import org.koin.android.ext.android.get
import kotlin.reflect.KProperty1

private val log = KLog.logger {}

data class ListState(val list: List<String> = listOf("1", "2", "3"), override val error: LocalizedException? = null) : BaseViewState()

class ListViewModel(
    initState: ListState,
    private val appCoroutineDispatchers: AppCoroutineDispatchers
) : BaseViewModel<ListState>(initState, log) {
    override fun getErrorProperty(): KProperty1<ListState, LocalizedException?> = ListState::error

    override fun ListState.copyWithError(localizedException: LocalizedException?) = copy(error = localizedException)

    companion object : MvRxViewModelFactory<ListViewModel, ListState> {
        override fun create(viewModelContext: ViewModelContext, state: ListState): ListViewModel? {
            return with(viewModelContext.activity) {
                ListViewModel(state, get())
            }
        }
    }
}
