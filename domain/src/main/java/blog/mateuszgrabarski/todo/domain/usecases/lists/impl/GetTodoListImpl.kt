package blog.mateuszgrabarski.todo.domain.usecases.lists.impl

import blog.mateuszgrabarski.todo.domain.models.TodoList
import blog.mateuszgrabarski.todo.domain.repositories.TodoListRepository
import blog.mateuszgrabarski.todo.domain.usecases.lists.GetTodoList
import blog.mateuszgrabarski.todo.domain.usecases.lists.GetTodoList.Arguments
import blog.mateuszgrabarski.todo.domain.usecases.utils.Failure
import blog.mateuszgrabarski.todo.domain.usecases.utils.UseCaseResult
import blog.mateuszgrabarski.todo.domain.usecases.utils.Success
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetTodoListImpl(
    private val repository: TodoListRepository
) : GetTodoList {

    override fun execute(argument: Arguments): Flow<UseCaseResult<TodoList>> = flow {
        val todoList = repository.getById(argument.listId)

        if (todoList == null) {
            emit(Failure(GetTodoList.ERROR_LIST_NOT_FOUND))
            return@flow
        }

        emit(Success(todoList))
    }
}