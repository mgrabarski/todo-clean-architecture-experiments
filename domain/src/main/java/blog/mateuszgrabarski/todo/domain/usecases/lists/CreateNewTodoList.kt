package blog.mateuszgrabarski.todo.domain.usecases.lists

import blog.mateuszgrabarski.todo.domain.models.TodoList
import blog.mateuszgrabarski.todo.domain.usecases.lists.CreateNewTodoList.Arguments
import blog.mateuszgrabarski.todo.domain.usecases.utils.ArgumentedUseCase
import blog.mateuszgrabarski.todo.domain.usecases.utils.Result

interface CreateNewTodoList : ArgumentedUseCase<Arguments, Result<TodoList>> {

    data class Arguments(
        val name: String,
        val description: String
    )

    companion object {
        const val ERROR_EMPTY_NAME = "name can not be empty"
    }
}