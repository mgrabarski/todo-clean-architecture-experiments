package blog.mateuszgrabarski.todo.domain.usecases.todo.impl

import blog.mateuszgrabarski.todo.domain.repositories.TodoRepository
import blog.mateuszgrabarski.todo.domain.repositories.isSuccess
import blog.mateuszgrabarski.todo.domain.usecases.todo.MarkTodoAsCompleted
import blog.mateuszgrabarski.todo.domain.usecases.todo.MarkTodoAsCompleted.Arguments
import blog.mateuszgrabarski.todo.domain.usecases.todo.MarkTodoAsCompleted.Companion.ERROR_TODO_NOT_FOUND
import blog.mateuszgrabarski.todo.domain.usecases.todo.MarkTodoAsCompleted.Companion.ERROR_UNKNOWN
import blog.mateuszgrabarski.todo.domain.usecases.utils.Failure
import blog.mateuszgrabarski.todo.domain.usecases.utils.UseCaseResult
import blog.mateuszgrabarski.todo.domain.usecases.utils.Success
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MarkTodoAsCompletedImpl(
    private val todoRepository: TodoRepository
) : MarkTodoAsCompleted {

    override fun execute(argument: Arguments): Flow<UseCaseResult<Boolean>> = flow {
        val todo = todoRepository.getById(argument.todoId)

        if (todo == null) {
            emit(Failure(ERROR_TODO_NOT_FOUND))
            return@flow
        }

        val result = todoRepository.markAsCompleted(todo)

        if (result.isSuccess()) {
            emit(Success(true))
        } else {
            emit(Failure(ERROR_UNKNOWN))
        }
    }
}