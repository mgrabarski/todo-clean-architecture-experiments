package blog.mateuszgrabarski.todo.domain.usecases.todo.impl

import blog.mateuszgrabarski.todo.domain.repositories.TodoRepository
import blog.mateuszgrabarski.todo.domain.usecases.todo.DeleteTodo
import blog.mateuszgrabarski.todo.domain.usecases.todo.DeleteTodo.Arguments
import blog.mateuszgrabarski.todo.domain.usecases.todo.DeleteTodo.Companion.ERROR_TODO_NOT_FOUND
import blog.mateuszgrabarski.todo.domain.usecases.utils.Failure
import blog.mateuszgrabarski.todo.domain.usecases.utils.Result
import blog.mateuszgrabarski.todo.domain.usecases.utils.Success
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeleteTodoImpl(
    private val repository: TodoRepository
) : DeleteTodo {


    override fun execute(argument: Arguments): Flow<Result<Boolean>> = flow {
        val todo = repository.getById(argument.todoId)

        if (todo == null) {
            emit(Failure(ERROR_TODO_NOT_FOUND))
            return@flow
        }

        repository.delete(todo)
        emit(Success(true))
    }
}