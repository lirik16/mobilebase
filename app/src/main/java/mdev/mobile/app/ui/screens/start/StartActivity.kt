package mdev.mobile.app.ui.screens.start

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentTransaction
import mdev.mobile.app.R
import mdev.mobile.app.databinding.ActivityStartBinding
import mdev.mobile.app.ui.base.BaseActivity

class StartActivity : BaseActivity() {
    private lateinit var binding: ActivityStartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_start)
        setSupportActionBar(binding.toolbar)

        supportFragmentManager
            .beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .replace(R.id.container, StartFragment.newInstance())
            .commit()
    }
}
