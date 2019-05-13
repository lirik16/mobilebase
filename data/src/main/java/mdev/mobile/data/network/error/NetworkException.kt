package mdev.mobile.data.network.error

import androidx.annotation.StringRes
import mdev.mobile.data.R
import mdev.mobile.domain.error.PermanentException

open class ServerResponseError(@StringRes userMessage: Int = R.string.error_unknown_error) : PermanentException(userMessage)

class ServerResponseFormatError : ServerResponseError()
class NullResponseBodyError : ServerResponseError()

class HttpError(val statusCode: Int) : PermanentException(R.string.error_http_error)

class ConnectionError(error: Throwable) : PermanentException(R.string.error_connection_error, cause = error)
