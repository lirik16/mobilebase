package mdev.mobile.data.network.util

import io.reactivex.Single
import mdev.mobile.base.kotlin.log.KLog
import mdev.mobile.data.network.error.ConnectionError
import mdev.mobile.data.network.error.HttpError
import mdev.mobile.data.network.error.NullResponseBodyError
import mdev.mobile.data.network.error.ServerResponseError
import mdev.mobile.data.network.rest.models.RestError
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import java.io.IOException

private val log = KLog.logger { }

class HttpResponseError(val statusCode: Int, val scError: RestError) : ServerResponseError()

internal inline fun <reified T> Single<Call<T>>.handleCall(): Single<T> = this
    .map { call -> innerHandleCall(call) }
    .onErrorResumeNext { it: Throwable -> Single.error(mapNetworkError(it)) }
    .cast(T::class.java)

@Suppress("TooGenericExceptionCaught")
internal fun <T> handleCall(call: Call<T>): T = try {
    innerHandleCall(call)
} catch (error: Throwable) {
    throw mapNetworkError(error)
}

private fun <T> innerHandleCall(call: Call<T>): T {
    val response = call.execute()
    if (response.isSuccessful) {
        val responseBody = response.body()
        if (responseBody != null) {
            return responseBody
        } else {
            throw NullResponseBodyError()
        }
    } else {
        val statusCode = response.raw().code()
        // TODO: check response code before parse, may be we don't need to parse at all
        try {
            response.errorBody()?.let { errorBody ->
                val scError = with(JSONObject(errorBody.string())) {
                    RestError(getInt("code"), getString("message"))
                }

                // TODO: define user's error message based on the error code
                throw HttpResponseError(statusCode, scError)
            }
        } catch (jsonException: JSONException) {
            log.error(jsonException) { "Error server response parsing" }
        }

        throw HttpError(statusCode)
    }
}

private fun mapNetworkError(error: Throwable): Throwable = when (error) {
    is IOException -> ConnectionError(error)
    else -> error
}
