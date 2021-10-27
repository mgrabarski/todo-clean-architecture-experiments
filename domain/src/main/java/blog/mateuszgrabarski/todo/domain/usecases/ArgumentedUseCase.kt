package blog.mateuszgrabarski.todo.domain.usecases

import kotlinx.coroutines.flow.Flow

interface ArgumentedUseCase<Argument, Result> {
    fun execute(argument: Argument): Flow<Result>
}