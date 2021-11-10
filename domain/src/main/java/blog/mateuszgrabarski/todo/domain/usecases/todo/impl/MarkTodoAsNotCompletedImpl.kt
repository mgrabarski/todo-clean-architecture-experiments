package blog.mateuszgrabarski.todo.domain.usecases.todo.impl

import blog.mateuszgrabarski.todo.domain.repositories.TodoRepository
import blog.mateuszgrabarski.todo.domain.usecases.todo.MarkTodoAsNotCompleted
import blog.mateuszgrabarski.todo.domain.usecases.todo.MarkTodoAsNotCompleted.Arguments
import blog.mateuszgrabarski.todo.domain.usecases.todo.MarkTodoAsNotCompleted.Companion.ERROR_ALREADY_COMPLETED
import blog.mateuszgrabarski.todo.domain.usecases.todo.MarkTodoAsNotCompleted.Companion.ERROR_TODO_NOT_FOUND
import blog.mateuszgrabarski.todo.domain.usecases.utils.Failure
import blog.mateuszgrabarski.todo.domain.usecases.utils.UseCaseResult
import blog.mateuszgrabarski.todo.domain.usecases.utils.Success
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MarkTodoAsNotCompletedImpl(
    private val repository: TodoRepository
) : MarkTodoAsNotCompleted {

    override fun execute(argument: Arguments): Flow<UseCaseResult<Boolean>> = flow {
        val todo = repository.getById(argument.todoId)

        if (todo == null) {
            emit(Failure(ERROR_TODO_NOT_FOUND))
            return@flow
        }

        if (!repository.isCompleted(todo.id)) {
            emit(Failure(ERROR_ALREADY_COMPLETED))
            return@flow
        }

        repository.markAsNotCompleted(todo)
        emit(Success(true))
    }
}