package mdev.mobile.app.util.logger

import mdev.mobile.base.kotlin.log.KLog
import org.koin.log.Logger

private val log = KLog.logger { }

class KoinLogger : Logger {
    override fun debug(msg: String) {
        log.debug { msg }
    }

    override fun err(msg: String) {
        log.error { msg }
    }

    override fun info(msg: String) {
        log.info { msg }
    }
}
