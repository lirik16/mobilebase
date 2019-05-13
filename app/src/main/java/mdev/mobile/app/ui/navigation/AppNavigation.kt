package mdev.mobile.app.ui.navigation

import android.app.Activity
import android.content.Intent
import mdev.mobile.app.ui.screens.start.StartActivity

// TODO: change to new Navigation
object AppNavigation {
    fun openStartScreen(activity: Activity) {
        activity.startActivity(Intent(activity, StartActivity::class.java))
        activity.finish()
    }
}
