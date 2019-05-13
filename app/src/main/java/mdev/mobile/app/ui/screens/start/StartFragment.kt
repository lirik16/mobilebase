package mdev.mobile.app.ui.screens.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenResumed
import com.airbnb.mvrx.fragmentViewModel
import kotlinx.coroutines.launch
import mdev.mobile.app.R
import mdev.mobile.app.databinding.FragmentStartBinding
import mdev.mobile.app.ui.base.BaseFragment
import mdev.mobile.app.ui.navigation.IntentCreator

class StartFragment : BaseFragment<StartViewModel, StartState, FragmentStartBinding>() {
    override val viewModel: StartViewModel by fragmentViewModel()

    companion object {
        fun newInstance() = StartFragment()
    }

    override fun inflateBinding(inflater: LayoutInflater) = FragmentStartBinding.inflate(inflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.menu_start, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.send_error_report -> sendErrorReport()
            R.id.send_feedback -> sendFeedback()
            else -> return super.onOptionsItemSelected(item)
        }

        return true
    }

    private fun sendFeedback() = startActivityWithChooser(R.string.intent_send_feedback_chooser_title) {
        IntentCreator.sendFeedback(getString(R.string.intent_send_feedback_subject), getString(R.string.intent_send_feedback_text))
    }

    private fun sendErrorReport() = lifecycleScope.launch {
        val logsUri = viewModel.getLogsUri(requireContext())
        whenResumed {
            startActivityWithChooser(R.string.intent_send_error_report_chooser_title) {
                IntentCreator.sendErrorReport(
                    getString(R.string.intent_send_error_report_subject),
                    getString(R.string.intent_send_error_report_text),
                    logsUri
                )
            }
        }
    }

    override fun invalidate() {
    }
}
