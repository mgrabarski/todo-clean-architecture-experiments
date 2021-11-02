package blog.mateuszgrabarski.todo.domain.usecases.utils

sealed class Result<out T : Any>
data class Success<out T : Any>(val data: T) : Result<T>()
data class Failure(val message: String) : Result<Nothing>()