package blog.mateuszgrabarski.todo.domain.usecases.utils

import kotlinx.coroutines.flow.Flow

interface UseCase<Result> {
    fun execute(): Flow<Result>
}

