package blog.mateuszgrabarski.todo.domain.usecases.lists.impl

import blog.mateuszgrabarski.todo.domain.data.factories.TodoListFactory
import blog.mateuszgrabarski.todo.domain.data.validators.TodoListNameValidator
import blog.mateuszgrabarski.todo.domain.models.TodoList
import blog.mateuszgrabarski.todo.domain.repositories.TodoListRepository
import blog.mateuszgrabarski.todo.domain.usecases.lists.CreateNewTodoList
import blog.mateuszgrabarski.todo.domain.usecases.lists.CreateNewTodoList.Arguments
import blog.mateuszgrabarski.todo.domain.usecases.utils.Failure
import blog.mateuszgrabarski.todo.domain.usecases.utils.Result
import blog.mateuszgrabarski.todo.domain.usecases.utils.Success
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CreateNewTodoListImpl(
    private val nameValidator: TodoListNameValidator,
    private val factory: TodoListFactory,
    private val repository: TodoListRepository
) : CreateNewTodoList {

    override suspend fun execute(argument: Arguments): Flow<Result<TodoList>> = flow {
        if (!nameValidator.isValid(argument.name)) {
            emit(Failure(ERROR_EMPTY_NAME))
            return@flow
        }

        val newList = factory.create(argument.name, argument.description)

        repository.saveList(newList)

        emit(Success(newList))
    }
}