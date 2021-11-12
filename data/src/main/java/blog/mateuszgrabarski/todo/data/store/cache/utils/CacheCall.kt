package blog.mateuszgrabarski.todo.data.store.cache.utils

import blog.mateuszgrabarski.todo.data.store.cache.CacheErrors.CACHE_ERROR_TIMEOUT
import blog.mateuszgrabarski.todo.data.store.cache.CacheErrors.CACHE_ERROR_UNKNOWN
import blog.mateuszgrabarski.todo.data.store.cache.CacheResult
import blog.mateuszgrabarski.todo.data.store.cache.CacheResult.GenericError
import blog.mateuszgrabarski.todo.data.store.cache.CacheResult.Success
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout

private const val TIMEOUT = 2_000L

suspend fun <T> safeCacheCall(
    dispatcher: CoroutineDispatcher,
    cacheCall: suspend () -> T?
): CacheResult<T?> {
    return withContext(dispatcher) {
        try {
            withTimeout(TIMEOUT) {
                Success(cacheCall.invoke())
            }
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
            when (throwable) {
                is TimeoutCancellationException -> GenericError(CACHE_ERROR_TIMEOUT)
                else -> GenericError(CACHE_ERROR_UNKNOWN)
            }
        }
    }
}