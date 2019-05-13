package mdev.mobile.app.ui.screens.splash

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import mdev.mobile.app.R
import mdev.mobile.app.databinding.ActivitySplashScreenBinding
import mdev.mobile.app.ui.base.BaseActivity
import mdev.mobile.app.ui.navigation.AppNavigation

class SplashScreenActivity : BaseActivity(), CoroutineScope by MainScope() {
    private lateinit var binding: ActivitySplashScreenBinding
    private val creationTime = System.currentTimeMillis()

    companion object {
        private const val MIN_SHOW_MILLIS = 1000L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash_screen)

        launch {
            val delayTime = (creationTime + MIN_SHOW_MILLIS) - System.currentTimeMillis()
            if (delayTime > 0) {
                delay(delayTime)
            }

            AppNavigation.openStartScreen(this@SplashScreenActivity)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
}
