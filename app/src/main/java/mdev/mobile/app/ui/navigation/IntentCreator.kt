package mdev.mobile.app.ui.navigation

import android.content.Intent
import android.net.Uri

object IntentCreator {
    private const val FEEDBACK_EMAIL_ADDRESS = "kbulgakovdev@gmail.com"
    private const val ERROR_REPORT_EMAIL_ADDRESS = "kbulgakovdev@gmail.com"
    private const val EMAIL_INTENT_TYPE = "text/plain"

    fun sendFeedback(subject: String, text: String) = Intent(Intent.ACTION_SEND).apply {
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_EMAIL, arrayOf(FEEDBACK_EMAIL_ADDRESS))
        putExtra(Intent.EXTRA_TEXT, text)
        type = EMAIL_INTENT_TYPE
    }

    fun sendErrorReport(subject: String, text: String, logsFilePath: Uri) = Intent(Intent.ACTION_SEND).apply {
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_EMAIL, arrayOf(ERROR_REPORT_EMAIL_ADDRESS))
        putExtra(Intent.EXTRA_TEXT, text)
        putExtra(Intent.EXTRA_STREAM, logsFilePath)
        flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        type = EMAIL_INTENT_TYPE
    }
}
