package blog.mateuszgrabarski.todo.domain.usecases.utils

sealed class UseCaseResult<out T : Any>
data class Success<out T : Any>(val data: T) : UseCaseResult<T>()
data class Failure(val message: String) : UseCaseResult<Nothing>()