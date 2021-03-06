package blog.mateuszgrabarski.todo.domain.usecases.todo

import blog.mateuszgrabarski.todo.domain.models.Id
import blog.mateuszgrabarski.todo.domain.models.Todo
import blog.mateuszgrabarski.todo.domain.usecases.todo.UpdateTodo.Arguments
import blog.mateuszgrabarski.todo.domain.usecases.utils.ArgumentedUseCase
import blog.mateuszgrabarski.todo.domain.usecases.utils.UseCaseResult

interface UpdateTodo : ArgumentedUseCase<Arguments, UseCaseResult<Todo>> {

    data class Arguments(
        val todoId: Id,
        val description: String
    )

    companion object {
        const val ERROR_TODO_NOT_FOUND = "Todo with id from arguments not found"
        const val ERROR_EMPTY_DESCRIPTION = "empty description"
        const val ERROR_UNKNOWN = "something went wrong"
    }
}