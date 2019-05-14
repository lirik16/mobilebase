package mdev.mobile.app.ui.screens.splash

import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenResumed
import com.airbnb.mvrx.fragmentViewModel
import kotlinx.coroutines.launch
import mdev.mobile.app.databinding.FragmentSplashBinding
import mdev.mobile.app.ui.base.BaseFragment
import mdev.mobile.app.ui.navigation.AppNavigation

class SplashFragment : BaseFragment<SplashViewModel, SplashState, FragmentSplashBinding>() {
    override val viewModel: SplashViewModel by fragmentViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            viewModel.splashScreenDelay()

            whenResumed {
                AppNavigation.openStartScreen(requireActivity())
            }
        }
    }

    override fun inflateBinding(inflater: LayoutInflater) = FragmentSplashBinding.inflate(inflater)


    override fun invalidate() {
    }
}
