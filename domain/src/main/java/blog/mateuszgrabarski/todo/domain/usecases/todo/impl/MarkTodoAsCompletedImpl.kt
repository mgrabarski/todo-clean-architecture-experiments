package blog.mateuszgrabarski.todo.domain.usecases.todo.impl

import blog.mateuszgrabarski.todo.domain.repositories.TodoRepository
import blog.mateuszgrabarski.todo.domain.usecases.todo.MarkTodoAsCompleted
import blog.mateuszgrabarski.todo.domain.usecases.todo.MarkTodoAsCompleted.Arguments
import blog.mateuszgrabarski.todo.domain.usecases.todo.MarkTodoAsCompleted.Companion.ERROR_TODO_NOT_FOUND
import blog.mateuszgrabarski.todo.domain.usecases.utils.Failure
import blog.mateuszgrabarski.todo.domain.usecases.utils.Result
import blog.mateuszgrabarski.todo.domain.usecases.utils.Success
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MarkTodoAsCompletedImpl(
    private val todoRepository: TodoRepository
) : MarkTodoAsCompleted {

    override suspend fun execute(argument: Arguments): Flow<Result<Boolean>> = flow {
        val todo = todoRepository.getById(argument.todoId)

        if (todo == null) {
            emit(Failure(ERROR_TODO_NOT_FOUND))
            return@flow
        }

        todoRepository.markAsCompleted(todo)
        emit(Success(true))
    }
}