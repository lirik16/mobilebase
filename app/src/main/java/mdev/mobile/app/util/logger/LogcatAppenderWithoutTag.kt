package mdev.mobile.app.util.logger

import android.util.Log
import ch.qos.logback.classic.Level
import ch.qos.logback.classic.PatternLayout
import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.UnsynchronizedAppenderBase

class LogcatAppenderWithoutTag : UnsynchronizedAppenderBase<ILoggingEvent>() {
    companion object {
        /**
         * Max tag length enforced by Android
         * http://developer.android.com/reference/android/util/Log.html#isLoggable(java.lang.String, int)
         */
        private const val MAX_TAG_LENGTH = 23
    }


    var encoder: PatternLayoutEncoder? = null
    var tagEncoder: PatternLayoutEncoder? = null
    var checkLoggable = false

    /**
     * Checks that required parameters are set, and if everything is in order,
     * activates this appender.
     */
    override fun start() {
        if (encoder?.layout == null) {
            addError("No layout set for the appender named [$name].")
            return
        }

        // tag encoder is optional but needs a layout
        tagEncoder?.let {
            val layout = it.layout

            if (layout == null) {
                addError("No tag layout set for the appender named [$name].")
                return
            }

            // prevent stack traces from showing up in the tag
            // (which could lead to very confusing error messages)
            if (layout is PatternLayout) {
                val pattern = it.pattern
                if (!pattern.contains("%nopex")) {
                    it.stop()
                    it.pattern = "$pattern%nopex"
                    it.start()
                }

                layout.setPostCompileProcessor(null)
            }
        }

        super.start()
    }

    /**
     * Writes an event to Android's logging mechanism (logcat)
     *
     * @param event
     * the event to be logged
     */
    public override fun append(event: ILoggingEvent) {
        if (!isStarted) {
            return
        }

        // We don't need that android Log print the TAG because logback already print logger info
        val tag = getTag(event)

        when (event.level.levelInt) {
            Level.ALL_INT, Level.TRACE_INT -> if (!checkLoggable || Log.isLoggable(tag, Log.VERBOSE)) {
                Log.v("T", this.encoder!!.layout.doLayout(event))
            }

            Level.DEBUG_INT -> if (!checkLoggable || Log.isLoggable(tag, Log.DEBUG)) {
                Log.d("D", this.encoder!!.layout.doLayout(event))
            }

            Level.INFO_INT -> if (!checkLoggable || Log.isLoggable(tag, Log.INFO)) {
                Log.i("I", this.encoder!!.layout.doLayout(event))
            }

            Level.WARN_INT -> if (!checkLoggable || Log.isLoggable(tag, Log.WARN)) {
                Log.w("W", this.encoder!!.layout.doLayout(event))
            }

            Level.ERROR_INT -> if (!checkLoggable || Log.isLoggable(tag, Log.ERROR)) {
                Log.e("E", this.encoder!!.layout.doLayout(event))
            }

            Level.OFF_INT -> {
            }
            else -> {
            }
        }
    }

    protected fun getTag(event: ILoggingEvent): String {
        // format tag based on encoder layout; truncate if max length
        // exceeded (only necessary for isLoggable(), which throws
        // IllegalArgumentException)
        var tag = tagEncoder?.layout?.doLayout(event) ?: event.loggerName
        if (checkLoggable && tag.length > MAX_TAG_LENGTH) {
            tag = tag.substring(0, MAX_TAG_LENGTH - 1) + "*"
        }
        return tag
    }
}
