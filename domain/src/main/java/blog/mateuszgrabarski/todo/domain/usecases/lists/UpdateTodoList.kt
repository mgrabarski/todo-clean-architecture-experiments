package blog.mateuszgrabarski.todo.domain.usecases.lists

import blog.mateuszgrabarski.todo.domain.models.Id
import blog.mateuszgrabarski.todo.domain.models.TodoList
import blog.mateuszgrabarski.todo.domain.usecases.lists.UpdateTodoList.Arguments
import blog.mateuszgrabarski.todo.domain.usecases.utils.ArgumentedUseCase
import blog.mateuszgrabarski.todo.domain.usecases.utils.Result


interface UpdateTodoList : ArgumentedUseCase<Arguments, Result<TodoList>> {

    data class Arguments(
        val listId: Id,
        val newName: String,
        val newDescription: String
    )


    val ERROR_LIST_NOT_FOUND
        get() = "list with id from arguments not found"

    val ERROR_EMPTY_NAME
        get() = "name can not be empty"
}