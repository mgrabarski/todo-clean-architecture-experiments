package blog.mateuszgrabarski.todo.domain.usecases

import kotlinx.coroutines.flow.Flow

interface UseCase<Result> {
    fun execute(): Flow<Result>
}

