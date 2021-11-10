package blog.mateuszgrabarski.todo.domain.usecases.lists

import blog.mateuszgrabarski.todo.domain.models.Id
import blog.mateuszgrabarski.todo.domain.models.TodoList
import blog.mateuszgrabarski.todo.domain.usecases.lists.GetTodoList.Arguments
import blog.mateuszgrabarski.todo.domain.usecases.utils.ArgumentedUseCase
import blog.mateuszgrabarski.todo.domain.usecases.utils.UseCaseResult

interface GetTodoList : ArgumentedUseCase<Arguments, UseCaseResult<TodoList>> {

    data class Arguments(
        val listId: Id
    )

    companion object {
        const val ERROR_LIST_NOT_FOUND = "list with id from arguments not found"
    }
}