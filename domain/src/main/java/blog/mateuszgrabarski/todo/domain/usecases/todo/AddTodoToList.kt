package blog.mateuszgrabarski.todo.domain.usecases.todo

import blog.mateuszgrabarski.todo.domain.models.Id
import blog.mateuszgrabarski.todo.domain.models.Todo
import blog.mateuszgrabarski.todo.domain.usecases.todo.AddTodoToList.Arguments
import blog.mateuszgrabarski.todo.domain.usecases.utils.ArgumentedUseCase

interface AddTodoToList : ArgumentedUseCase<Arguments, Todo> {

    data class Arguments(
        val listId: Id,
        val description: String
    )

    companion object {
        const val ERROR_LIST_NOT_FOUND = "list with id from arguments not found"
        const val ERROR_EMPTY_DESCRIPTION = "empty description"
    }
}