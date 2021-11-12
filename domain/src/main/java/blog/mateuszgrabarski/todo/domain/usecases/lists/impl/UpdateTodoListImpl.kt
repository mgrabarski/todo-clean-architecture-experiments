package blog.mateuszgrabarski.todo.domain.usecases.lists.impl

import blog.mateuszgrabarski.todo.domain.data.validators.TodoListNameValidator
import blog.mateuszgrabarski.todo.domain.models.TodoList
import blog.mateuszgrabarski.todo.domain.repositories.TodoListRepository
import blog.mateuszgrabarski.todo.domain.repositories.isSuccess
import blog.mateuszgrabarski.todo.domain.usecases.lists.UpdateTodoList
import blog.mateuszgrabarski.todo.domain.usecases.lists.UpdateTodoList.Arguments
import blog.mateuszgrabarski.todo.domain.usecases.lists.UpdateTodoList.Companion.ERROR_EMPTY_NAME
import blog.mateuszgrabarski.todo.domain.usecases.lists.UpdateTodoList.Companion.ERROR_LIST_NOT_FOUND
import blog.mateuszgrabarski.todo.domain.usecases.lists.UpdateTodoList.Companion.ERROR_UNKNOWN
import blog.mateuszgrabarski.todo.domain.usecases.utils.Failure
import blog.mateuszgrabarski.todo.domain.usecases.utils.Success
import blog.mateuszgrabarski.todo.domain.usecases.utils.UseCaseResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.joda.time.DateTime

class UpdateTodoListImpl(
    private val nameValidator: TodoListNameValidator,
    private val repository: TodoListRepository
) : UpdateTodoList {

    override fun execute(argument: Arguments): Flow<UseCaseResult<TodoList>> = flow {
        if (!nameValidator.isValid(argument.newName)) {
            emit(Failure(ERROR_EMPTY_NAME))
            return@flow
        }

        val list = repository.getById(argument.listId)

        if (list == null) {
            emit(Failure(ERROR_LIST_NOT_FOUND))
            return@flow
        }

        with(list) {
            name = argument.newName
            description = argument.newDescription
            modificationDateTime = DateTime.now()
        }

        val result = repository.update(list)

        if (result.isSuccess()) {
            emit(Success(list))
        } else {
            emit(Failure(ERROR_UNKNOWN))
        }
    }
}