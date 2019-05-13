package mdev.mobile.app.ui.base

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.databinding.ViewDataBinding
import com.airbnb.mvrx.BaseMvRxFragment

abstract class BaseFragment<VM : BaseViewModel<S>, S : BaseViewState, B : ViewDataBinding> : BaseMvRxFragment() {
    protected abstract val viewModel: VM
    private lateinit var _binding: B
    protected val binding: B
        get() = _binding

    val baseActivity
        get() = activity as? BaseActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // https://github.com/airbnb/MvRx/issues/118
        viewModel.selectSubscribe(viewModel.getErrorProperty(), uniqueOnly(), subscriber = {
            if (handleError(it, requireContext(), view)) {
                viewModel.clearError(it)
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = inflateBinding(inflater)
        _binding.lifecycleOwner = viewLifecycleOwner
        return _binding.root
    }

    abstract fun inflateBinding(inflater: LayoutInflater): B

    inline fun startActivityWithChooser(@StringRes title: Int, intentCreator: () -> Intent) = startActivity(
        Intent.createChooser(intentCreator(), getString(title))
    )
}
