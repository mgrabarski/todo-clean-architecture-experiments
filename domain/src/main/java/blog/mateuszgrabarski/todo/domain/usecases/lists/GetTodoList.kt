package blog.mateuszgrabarski.todo.domain.usecases.lists

import blog.mateuszgrabarski.todo.domain.models.Id
import blog.mateuszgrabarski.todo.domain.models.TodoList
import blog.mateuszgrabarski.todo.domain.usecases.utils.ArgumentedUseCase

interface GetTodoList : ArgumentedUseCase<Id, Result<TodoList>> {

    companion object {
        const val ERROR_LIST_NOT_FOUND = "list with id from arguments not found"
    }
}