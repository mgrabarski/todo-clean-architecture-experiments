package blog.mateuszgrabarski.todo.domain.usecases.todo

import blog.mateuszgrabarski.todo.domain.models.Id
import blog.mateuszgrabarski.todo.domain.usecases.todo.DeleteTodo.Arguments
import blog.mateuszgrabarski.todo.domain.usecases.utils.ArgumentedUseCase
import blog.mateuszgrabarski.todo.domain.usecases.utils.UseCaseResult

interface DeleteTodo : ArgumentedUseCase<Arguments, UseCaseResult<Boolean>> {

    data class Arguments(
        val todoId: Id
    )

    companion object {
        const val ERROR_TODO_NOT_FOUND = "Todo with id from arguments not found"
    }
}