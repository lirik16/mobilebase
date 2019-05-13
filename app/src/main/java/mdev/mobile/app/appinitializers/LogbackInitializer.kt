package mdev.mobile.app.appinitializers

import android.content.Context
import ch.qos.logback.classic.Level
import ch.qos.logback.classic.LoggerContext
import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.rolling.FixedWindowRollingPolicy
import ch.qos.logback.core.rolling.RollingFileAppender
import ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy
import ch.qos.logback.core.util.FileSize
import mdev.mobile.app.util.common.CI_BUILD_MODE
import mdev.mobile.app.util.common.DEVELOPER_MODE
import mdev.mobile.app.util.common.FILE_PATH_LOG_PATH
import mdev.mobile.app.util.logger.LogcatAppenderWithoutTag
import mdev.mobile.base.kotlin.util.Initializer
import org.slf4j.LoggerFactory
import java.io.File
import java.util.Properties

class LogbackInitializer(private val context: Context) : Initializer {
    companion object {
        private const val LOGS_PATH_PROPERTY_KEY = "LOG_PATH"
        private const val ROLLING_POLICY_MIN_INDEX = 1
        private const val ROLLING_POLICY_MAX_INDEX = 3

        fun getLogsPath() = (LoggerFactory.getILoggerFactory() as LoggerContext).getProperty(LOGS_PATH_PROPERTY_KEY)
    }

    override fun init() {
        val loggerContext = LoggerFactory.getILoggerFactory() as LoggerContext
        with(loggerContext.getLogger("ROOT")) {
            if (!CI_BUILD_MODE) {
                addAppender(createLogcatAppender(loggerContext))
            }
            if (DEVELOPER_MODE) {
                addAppender(createFileAppender(loggerContext))
            }
            level = Level.ALL
            isAdditive = false
        }
    }

    private fun createFileAppender(loggerContext: LoggerContext): RollingFileAppender<ILoggingEvent> {
        val filePattern = PatternLayoutEncoder().apply {
            pattern = "%date{ISO8601}[%relative] [%thread] %-5level %logger{5}: %msg%n"
            context = loggerContext
            start()
        }

        val logsPath = createLogsPath()
        val logFileAppender = RollingFileAppender<ILoggingEvent>().apply {
            name = "file"
            file = "$logsPath${File.separator}test.log"
            encoder = filePattern
            context = loggerContext
            isAppend = true
        }

        val sizeBasedTriggeringPolicy = SizeBasedTriggeringPolicy<ILoggingEvent>().apply {
            context = loggerContext
            maxFileSize = FileSize.valueOf("3MB")
            start()
        }
        logFileAppender.triggeringPolicy = sizeBasedTriggeringPolicy

        val fixedWindowRollingPolicy = FixedWindowRollingPolicy().apply {
            context = loggerContext
            setParent(logFileAppender)
            fileNamePattern = "$logsPath${File.separator}test.%i.log.zip"
            minIndex = ROLLING_POLICY_MIN_INDEX
            maxIndex = ROLLING_POLICY_MAX_INDEX
            start()
        }

        logFileAppender.apply {
            rollingPolicy = fixedWindowRollingPolicy
            start()
        }

        val loggerContextProperties = Properties()
        loggerContextProperties[LOGS_PATH_PROPERTY_KEY] = logsPath
        loggerContext.putProperties(loggerContextProperties)
        return logFileAppender
    }

    private fun createLogcatAppender(loggerContext: LoggerContext): LogcatAppenderWithoutTag {
        val logcatPattern = PatternLayoutEncoder().apply {
            pattern = "%logger{5}: %msg [%thread]%n"
            context = loggerContext
            start()
        }

        val logcatAppender = LogcatAppenderWithoutTag().apply {
            name = "logcat"
            encoder = logcatPattern
            context = loggerContext
            start()
        }
        return logcatAppender
    }

    private fun createLogsPath() = File(context.filesDir, FILE_PATH_LOG_PATH).absolutePath
}
