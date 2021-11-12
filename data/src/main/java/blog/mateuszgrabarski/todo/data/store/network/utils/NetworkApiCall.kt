package blog.mateuszgrabarski.todo.data.store.network.utils

import blog.mateuszgrabarski.todo.data.store.network.NetworkApiResult
import blog.mateuszgrabarski.todo.data.store.network.NetworkApiResult.*
import blog.mateuszgrabarski.todo.data.store.network.NetworkErrors.NETWORK_ERROR_TIMEOUT
import blog.mateuszgrabarski.todo.data.store.network.NetworkErrors.NETWORK_ERROR_UNKNOWN
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import java.io.IOException

private const val TIMEOUT = 6_000L

suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> T?
): NetworkApiResult<T?> {
    return withContext(dispatcher) {
        try {
            withTimeout(TIMEOUT) {
                Success(apiCall.invoke())
            }
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
            when (throwable) {
                is TimeoutCancellationException -> {
                    val code = 408
                    GenericError(code, NETWORK_ERROR_TIMEOUT)
                }
                is IOException -> NetworkError
                else -> GenericError(null, NETWORK_ERROR_UNKNOWN)
            }
        }
    }
}