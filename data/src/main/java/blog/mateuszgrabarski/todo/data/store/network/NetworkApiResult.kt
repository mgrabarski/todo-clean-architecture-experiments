package blog.mateuszgrabarski.todo.data.store.network

sealed class NetworkApiResult<out T> {

    data class Success<out T>(val value: T) : NetworkApiResult<T>()

    data class GenericError(
        val code: Int? = null,
        val errorMessage: String? = null
    ) : NetworkApiResult<Nothing>()

    object NetworkError : NetworkApiResult<Nothing>()
}