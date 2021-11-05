package blog.mateuszgrabarski.todo.domain.usecases.lists.impl

import blog.mateuszgrabarski.todo.domain.repositories.TodoListRepository
import blog.mateuszgrabarski.todo.domain.usecases.lists.DeleteTodoList
import blog.mateuszgrabarski.todo.domain.usecases.lists.DeleteTodoList.Arguments
import blog.mateuszgrabarski.todo.domain.usecases.lists.DeleteTodoList.Companion.ERROR_LIST_NOT_FOUND
import blog.mateuszgrabarski.todo.domain.usecases.utils.Failure
import blog.mateuszgrabarski.todo.domain.usecases.utils.Result
import blog.mateuszgrabarski.todo.domain.usecases.utils.Success
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeleteTodoListImpl(
    private val repository: TodoListRepository
) : DeleteTodoList {

    override suspend fun execute(argument: Arguments): Flow<Result<Boolean>> = flow {
        val list = repository.getById(argument.listId)

        if (list == null) {
            emit(Failure(ERROR_LIST_NOT_FOUND))
            return@flow
        }

        repository.delete(argument.listId)
        emit(Success(true))
    }
}