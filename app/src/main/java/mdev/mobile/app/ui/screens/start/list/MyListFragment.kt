package mdev.mobile.app.ui.screens.start.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import mdev.mobile.app.databinding.FragmentListBinding
import mdev.mobile.app.ui.base.BaseFragment

class MyListFragment : BaseFragment<ListViewModel, ListState, FragmentListBinding>() {
    override val viewModel: ListViewModel by fragmentViewModel()
    private lateinit var controller: ListController

    companion object {
        fun newInstance() = MyListFragment()
    }

    override fun inflateBinding(inflater: LayoutInflater) = FragmentListBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        controller = ListController()
        binding.news.setController(controller)
    }

    override fun invalidate() {
        withState(viewModel) { state ->
            controller.setData(state)
        }
    }
}
