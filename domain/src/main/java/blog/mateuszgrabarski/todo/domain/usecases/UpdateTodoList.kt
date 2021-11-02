package blog.mateuszgrabarski.todo.domain.usecases

import blog.mateuszgrabarski.todo.domain.models.Id
import blog.mateuszgrabarski.todo.domain.models.TodoList
import blog.mateuszgrabarski.todo.domain.repositories.TodoListRepository
import blog.mateuszgrabarski.todo.domain.usecases.UpdateTodoList.Arguments
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UpdateTodoList(
    private val repository: TodoListRepository
) : ArgumentedUseCase<Arguments, Result<TodoList>> {

    override suspend fun execute(argument: Arguments): Flow<Result<TodoList>> = flow {
        val list = repository.getById(argument.listId)

        if (list == null) {
            Failure(ERROR_LIST_NOT_FOUND)
            return@flow
        }
    }

    data class Arguments(
        val listId: Id,
        val newName: String,
        val newDescription: String
    )

    companion object {
        const val ERROR_LIST_NOT_FOUND = "list with id from arguments not found"
    }
}