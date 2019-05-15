package mdev.mobile.app.ui.screens.start.list

import com.airbnb.epoxy.TypedEpoxyController
import mdev.mobile.app.listItem

class ListController : TypedEpoxyController<ListState>() {
    override fun buildModels(state: ListState?) {
        if (state == null) {
            return
        }

        state.list.forEach {
            listItem {
                id(it.hashCode())
                itemText(it)
            }
        }
    }
}
