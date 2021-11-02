package blog.mateuszgrabarski.todo.domain.usecases

import blog.mateuszgrabarski.todo.domain.data.validators.TodoListNameValidator
import blog.mateuszgrabarski.todo.domain.models.Id
import blog.mateuszgrabarski.todo.domain.models.TodoList
import blog.mateuszgrabarski.todo.domain.repositories.TodoListRepository
import blog.mateuszgrabarski.todo.domain.usecases.UpdateTodoList.Arguments
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.joda.time.DateTime

class UpdateTodoList(
    private val nameValidator: TodoListNameValidator,
    private val repository: TodoListRepository
) : ArgumentedUseCase<Arguments, Result<TodoList>> {

    override suspend fun execute(argument: Arguments): Flow<Result<TodoList>> = flow {
        if (!nameValidator.isValid(argument.newName)) {
            emit(Failure(ERROR_EMPTY_NAME))
            return@flow
        }

        val list = repository.getById(argument.listId)

        if (list == null) {
            Failure(ERROR_LIST_NOT_FOUND)
            return@flow
        }

        with(list) {
            name = argument.newName
            description = argument.newDescription
            modificationDateTime = DateTime.now()
        }

        repository.update(list)
        emit(Success(list))
    }

    data class Arguments(
        val listId: Id,
        val newName: String,
        val newDescription: String
    )

    companion object {
        const val ERROR_LIST_NOT_FOUND = "list with id from arguments not found"
        const val ERROR_EMPTY_NAME = "name can not be empty"
    }
}