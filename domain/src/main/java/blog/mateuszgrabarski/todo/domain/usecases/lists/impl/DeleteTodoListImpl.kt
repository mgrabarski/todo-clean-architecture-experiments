package blog.mateuszgrabarski.todo.domain.usecases.lists.impl

import blog.mateuszgrabarski.todo.domain.repositories.TodoListRepository
import blog.mateuszgrabarski.todo.domain.repositories.isSuccess
import blog.mateuszgrabarski.todo.domain.usecases.lists.DeleteTodoList
import blog.mateuszgrabarski.todo.domain.usecases.lists.DeleteTodoList.Arguments
import blog.mateuszgrabarski.todo.domain.usecases.lists.DeleteTodoList.Companion.ERROR_LIST_NOT_FOUND
import blog.mateuszgrabarski.todo.domain.usecases.lists.DeleteTodoList.Companion.ERROR_UNKNOWN
import blog.mateuszgrabarski.todo.domain.usecases.utils.Failure
import blog.mateuszgrabarski.todo.domain.usecases.utils.Success
import blog.mateuszgrabarski.todo.domain.usecases.utils.UseCaseResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeleteTodoListImpl(
    private val repository: TodoListRepository
) : DeleteTodoList {

    override fun execute(argument: Arguments): Flow<UseCaseResult<Boolean>> = flow {
        val list = repository.getById(argument.listId)

        if (list == null) {
            emit(Failure(ERROR_LIST_NOT_FOUND))
            return@flow
        }

        val result = repository.delete(argument.listId)

        if (result.isSuccess()) {
            emit(Success(result))
        } else {
            emit(Failure(ERROR_UNKNOWN))
        }
    }
}