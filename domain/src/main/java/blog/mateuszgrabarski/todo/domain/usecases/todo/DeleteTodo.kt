package blog.mateuszgrabarski.todo.domain.usecases.todo

import blog.mateuszgrabarski.todo.domain.models.Id
import blog.mateuszgrabarski.todo.domain.usecases.todo.DeleteTodo.Arguments
import blog.mateuszgrabarski.todo.domain.usecases.utils.ArgumentedUseCase
import blog.mateuszgrabarski.todo.domain.usecases.utils.Result

interface DeleteTodo : ArgumentedUseCase<Arguments, Result<Boolean>> {

    data class Arguments(
        val todoId: Id
    )

    companion object {
        const val ERROR_TODO_NOT_FOUND = "Todo with id from arguments not found"
    }
}