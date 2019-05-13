package mdev.mobile.app.appinitializers

import androidx.paging.RxPagedListBuilder
import mdev.mobile.base.kotlin.util.Initializer

// TODO: temporary disable async model building because I get crashes when pull-to-refresh with wrong notify thread.
//  Investigate it in the future
class EpoxyInitializer : Initializer {

    companion object {
        fun <T> setSchedulers(pagedListBuilder: RxPagedListBuilder<Int, T>) {
            // Set notify thread according to https://github.com/airbnb/epoxy/wiki/Paging-Support
//            pagedListBuilder.setNotifyScheduler(AndroidSchedulers.from(EpoxyController.defaultModelBuildingHandler.looper))
        }
    }

    override fun init() {
        // TODO: Make EpoxyController async
//        val handlerThread = HandlerThread("epoxy_thread")
//        handlerThread.start()
//
//        Handler(handlerThread.looper).also {
//            EpoxyController.defaultDiffingHandler = it
//            EpoxyController.defaultModelBuildingHandler = it
//        }
    }
}
