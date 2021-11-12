package blog.mateuszgrabarski.todo.domain.usecases.todo.impl

import blog.mateuszgrabarski.todo.domain.repositories.TodoRepository
import blog.mateuszgrabarski.todo.domain.repositories.isSuccess
import blog.mateuszgrabarski.todo.domain.usecases.todo.DeleteTodo
import blog.mateuszgrabarski.todo.domain.usecases.todo.DeleteTodo.Arguments
import blog.mateuszgrabarski.todo.domain.usecases.todo.DeleteTodo.Companion.ERROR_TODO_NOT_FOUND
import blog.mateuszgrabarski.todo.domain.usecases.todo.DeleteTodo.Companion.ERROR_UNKNOWN
import blog.mateuszgrabarski.todo.domain.usecases.utils.Failure
import blog.mateuszgrabarski.todo.domain.usecases.utils.Success
import blog.mateuszgrabarski.todo.domain.usecases.utils.UseCaseResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeleteTodoImpl(
    private val repository: TodoRepository
) : DeleteTodo {


    override fun execute(argument: Arguments): Flow<UseCaseResult<Boolean>> = flow {
        val todo = repository.getById(argument.todoId)

        if (todo == null) {
            emit(Failure(ERROR_TODO_NOT_FOUND))
            return@flow
        }

        val result = repository.delete(todo)

        if (result.isSuccess()) {
            emit(Success(true))
        } else {
            emit(Failure(ERROR_UNKNOWN))
        }
    }
}