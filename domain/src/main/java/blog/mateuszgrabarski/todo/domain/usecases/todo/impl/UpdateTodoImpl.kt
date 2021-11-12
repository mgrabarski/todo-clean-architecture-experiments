package blog.mateuszgrabarski.todo.domain.usecases.todo.impl

import blog.mateuszgrabarski.todo.domain.data.validators.TodoDescriptionValidator
import blog.mateuszgrabarski.todo.domain.models.Todo
import blog.mateuszgrabarski.todo.domain.repositories.TodoRepository
import blog.mateuszgrabarski.todo.domain.repositories.isSuccess
import blog.mateuszgrabarski.todo.domain.usecases.todo.UpdateTodo
import blog.mateuszgrabarski.todo.domain.usecases.todo.UpdateTodo.Arguments
import blog.mateuszgrabarski.todo.domain.usecases.todo.UpdateTodo.Companion.ERROR_EMPTY_DESCRIPTION
import blog.mateuszgrabarski.todo.domain.usecases.todo.UpdateTodo.Companion.ERROR_TODO_NOT_FOUND
import blog.mateuszgrabarski.todo.domain.usecases.todo.UpdateTodo.Companion.ERROR_UNKNOWN
import blog.mateuszgrabarski.todo.domain.usecases.utils.Failure
import blog.mateuszgrabarski.todo.domain.usecases.utils.Success
import blog.mateuszgrabarski.todo.domain.usecases.utils.UseCaseResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.joda.time.DateTime

class UpdateTodoImpl(
    private val repository: TodoRepository,
    private val validator: TodoDescriptionValidator
) : UpdateTodo {

    override fun execute(argument: Arguments): Flow<UseCaseResult<Todo>> = flow {
        val todo = repository.getById(argument.todoId)

        if (todo == null) {
            emit(Failure(ERROR_TODO_NOT_FOUND))
            return@flow
        }

        if (!validator.validate(argument.description)) {
            emit(Failure(ERROR_EMPTY_DESCRIPTION))
            return@flow
        }

        with(todo) {
            description = argument.description
            modificationDate = DateTime.now()
        }

        val result = repository.update(todo)

        if (result.isSuccess()) {
            emit(Success(todo))
        } else {
            emit(Failure(ERROR_UNKNOWN))
        }
    }
}