package blog.mateuszgrabarski.todo.data.store.cache

sealed class CacheResult<out T> {
    data class Success<out T>(val value: T) : CacheResult<T>()
    data class GenericError(val errorMessage: String? = null) : CacheResult<Nothing>()
}