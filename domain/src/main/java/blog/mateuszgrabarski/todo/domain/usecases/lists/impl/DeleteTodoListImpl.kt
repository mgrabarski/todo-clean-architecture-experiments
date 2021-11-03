package blog.mateuszgrabarski.todo.domain.usecases.lists.impl

import blog.mateuszgrabarski.todo.domain.models.Id
import blog.mateuszgrabarski.todo.domain.repositories.TodoListRepository
import blog.mateuszgrabarski.todo.domain.usecases.lists.DeleteTodoList
import blog.mateuszgrabarski.todo.domain.usecases.lists.UpdateTodoList
import blog.mateuszgrabarski.todo.domain.usecases.utils.Failure
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeleteTodoListImpl(
    private val repository: TodoListRepository
) : DeleteTodoList {

    override suspend fun execute(argument: Id): Flow<Result<Boolean>> = flow {
        val list = repository.getById(argument)

        if (list == null) {
            Failure(UpdateTodoList.ERROR_LIST_NOT_FOUND)
            return@flow
        }

        repository.delete(argument)
    }
}