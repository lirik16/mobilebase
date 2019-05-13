package mdev.mobile.app.ui.base

import com.airbnb.mvrx.MvRxState
import mdev.mobile.domain.error.LocalizedException

abstract class BaseViewState : MvRxState {
    abstract val error: LocalizedException?
}
