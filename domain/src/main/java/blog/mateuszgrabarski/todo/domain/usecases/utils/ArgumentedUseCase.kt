package blog.mateuszgrabarski.todo.domain.usecases.utils

import kotlinx.coroutines.flow.Flow

interface ArgumentedUseCase<Argument, Result> {
    suspend fun execute(argument: Argument): Flow<Result>
}