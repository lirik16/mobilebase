package mdev.mobile.app.ui.base

import android.content.Context
import android.view.View
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import mdev.mobile.domain.error.LocalizedException
import mdev.mobile.domain.error.PermanentException
import mdev.mobile.domain.error.RecoverableException
import mdev.mobile.domain.error.RetrievableException

fun handleError(error: LocalizedException?, context: Context, rootView: View?): Boolean {
    if (error == null) {
        return false
    }

    val message = when (error) {
        is RecoverableException -> context.getLocalizedMessage(error)
        // TODO: add full exception handling including retry button
        is RetrievableException -> context.getLocalizedMessage(error)
        is PermanentException -> context.getLocalizedMessage(error)
    }
    if (rootView != null) {
        Snackbar.make(rootView, message, Snackbar.LENGTH_LONG)
            .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
            .show()
    }

    return true
}

fun Context.getLocalizedMessage(localizedException: LocalizedException): String {
    return getString(localizedException.userMessage)
}
