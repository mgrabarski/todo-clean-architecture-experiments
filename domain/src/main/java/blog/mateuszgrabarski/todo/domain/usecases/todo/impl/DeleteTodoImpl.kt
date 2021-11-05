package blog.mateuszgrabarski.todo.domain.usecases.todo.impl

import blog.mateuszgrabarski.todo.domain.repositories.TodoRepository
import blog.mateuszgrabarski.todo.domain.usecases.todo.DeleteTodo
import blog.mateuszgrabarski.todo.domain.usecases.todo.DeleteTodo.Arguments
import blog.mateuszgrabarski.todo.domain.usecases.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeleteTodoImpl(
    private val repository: TodoRepository
) : DeleteTodo {

    override suspend fun execute(argument: Arguments): Flow<Result<Boolean>> = flow {
    }
}