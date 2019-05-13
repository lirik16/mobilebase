package mdev.mobile.domain.error

import androidx.annotation.StringRes

// Exception class with string resource for localized message
sealed class LocalizedException(
    @StringRes val userMessage: Int,
    override val message: String? = null,
    override val cause: Throwable? = null
) : Exception(message, cause)

// The program can try to recover from this exception by retry
open class RecoverableException(@StringRes userMessage: Int, message: String? = null, cause: Throwable? = null) :
    LocalizedException(userMessage, message, cause)

// The user can try to recover from this exception by retry
open class RetrievableException(@StringRes userMessage: Int, message: String? = null, cause: Throwable? = null) :
    LocalizedException(userMessage, message, cause)

open class PermanentException(@StringRes userMessage: Int, message: String? = null, cause: Throwable? = null) :
    LocalizedException(userMessage, message, cause)
