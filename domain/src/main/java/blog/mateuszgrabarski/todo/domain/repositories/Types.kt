package blog.mateuszgrabarski.todo.domain.repositories

typealias Success = Boolean

internal fun Boolean.isSuccess(): Boolean = this