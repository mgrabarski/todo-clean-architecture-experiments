package blog.mateuszgrabarski.todo.domain.usecases.todo.impl

import blog.mateuszgrabarski.todo.domain.data.factories.TodoFactory
import blog.mateuszgrabarski.todo.domain.data.validators.TodoDescriptionValidator
import blog.mateuszgrabarski.todo.domain.models.Todo
import blog.mateuszgrabarski.todo.domain.repositories.TodoListRepository
import blog.mateuszgrabarski.todo.domain.repositories.TodoRepository
import blog.mateuszgrabarski.todo.domain.usecases.todo.AddTodoToList
import blog.mateuszgrabarski.todo.domain.usecases.todo.AddTodoToList.Arguments
import blog.mateuszgrabarski.todo.domain.usecases.todo.AddTodoToList.Companion.ERROR_EMPTY_DESCRIPTION
import blog.mateuszgrabarski.todo.domain.usecases.todo.AddTodoToList.Companion.ERROR_LIST_NOT_FOUND
import blog.mateuszgrabarski.todo.domain.usecases.utils.Failure
import blog.mateuszgrabarski.todo.domain.usecases.utils.UseCaseResult
import blog.mateuszgrabarski.todo.domain.usecases.utils.Success
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AddTodoToListImpl(
    private val descriptionValidator: TodoDescriptionValidator,
    private val todoFactory: TodoFactory,
    private val listRepository: TodoListRepository,
    private val todoRepository: TodoRepository
) : AddTodoToList {

    override fun execute(argument: Arguments): Flow<UseCaseResult<Todo>> = flow {
        if (!descriptionValidator.validate(argument.description)) {
            emit(Failure(ERROR_EMPTY_DESCRIPTION))
            return@flow
        }

        val list = listRepository.getById(argument.listId)

        if (list == null) {
            emit(Failure(ERROR_LIST_NOT_FOUND))
            return@flow
        }

        val todo = todoFactory.create(argument.description, argument.listId)

        list.addTodo(todo)
        todoRepository.saveTodo(todo)
        emit(Success(todo))
    }
}