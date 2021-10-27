package blog.mateuszgrabarski.todo.domain.usecases

import blog.mateuszgrabarski.todo.domain.data.factories.TodoListFactory
import blog.mateuszgrabarski.todo.domain.models.TodoList
import blog.mateuszgrabarski.todo.domain.repositories.TodoListRepository
import blog.mateuszgrabarski.todo.domain.usecases.CreateNewTodoList.Argument
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CreateNewTodoList(
    private val factory: TodoListFactory,
    private val repository: TodoListRepository
) : ArgumentedUseCase<Argument, Result<TodoList>> {

    override fun execute(argument: Argument): Flow<Result<TodoList>> = flow {
        if (argument.name.isEmpty()) {
            emit(Failure(ERROR_EMPTY_NAME))
            return@flow
        }

        val newList = factory.create(argument.name, argument.description)

        repository.saveList(newList)

        emit(Success(newList))
    }

    data class Argument(
        val name: String,
        val description: String
    )

    companion object {
        const val ERROR_EMPTY_NAME = "name can not be empty"
    }
}