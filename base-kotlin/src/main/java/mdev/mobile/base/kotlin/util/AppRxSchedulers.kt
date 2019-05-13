package mdev.mobile.base.kotlin.util

import io.reactivex.Scheduler

data class AppRxSchedulers(val io: Scheduler, val computation: Scheduler, val main: Scheduler)
