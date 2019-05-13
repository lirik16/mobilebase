package mdev.mobile.app.ui.base

import android.annotation.SuppressLint
import com.airbnb.mvrx.BaseMvRxActivity
import mdev.mobile.app.App

@SuppressLint("Registered")
open class BaseActivity : BaseMvRxActivity() {
    val app
        get() = application as App
}
