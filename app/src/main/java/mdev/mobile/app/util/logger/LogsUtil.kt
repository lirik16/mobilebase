package mdev.mobile.app.util.logger

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider.getUriForFile
import mdev.mobile.app.appinitializers.LogbackInitializer
import mdev.mobile.app.util.common.FILE_PROVIDER_AUTHORITY
import mdev.mobile.base.kotlin.log.KLog
import java.io.File
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

private const val LOG_ZIP_FILE_NAME = "logs.zip"

private val log = KLog.logger { }

fun createLogsZip(context: Context): Uri {
    val logPath = LogbackInitializer.getLogsPath()
    val zipFilePath = "$logPath${File.separator}$LOG_ZIP_FILE_NAME"
    log.debug { "Create logs zip: $zipFilePath" }

    packToZip(logPath, zipFilePath)

    return getUriForFile(context, FILE_PROVIDER_AUTHORITY, File(zipFilePath))
}

private fun packToZip(sourceDirPath: String, zipFilePath: String) {
    val newFile = File(zipFilePath)
    if (newFile.exists()) {
        newFile.delete()
    }
    // TODO: add check is file created
    newFile.createNewFile()

    ZipOutputStream(newFile.outputStream()).use { stream ->
        val sourceDir = File(sourceDirPath)
        sourceDir.listFiles()
            .filter { !it.isDirectory && it.name != LOG_ZIP_FILE_NAME }
            .forEach {
                val zipEntry = ZipEntry(it.path.substring(sourceDir.toString().length + 1))
                stream.putNextEntry(zipEntry)
                stream.write(it.readBytes())
                stream.closeEntry()
            }
    }
}
