package blog.mateuszgrabarski.todo.domain.usecases.todo.impl

import blog.mateuszgrabarski.todo.domain.data.factories.TodoFactory
import blog.mateuszgrabarski.todo.domain.models.Todo
import blog.mateuszgrabarski.todo.domain.repositories.TodoListRepository
import blog.mateuszgrabarski.todo.domain.repositories.TodoRepository
import blog.mateuszgrabarski.todo.domain.usecases.todo.AddTodoToList
import blog.mateuszgrabarski.todo.domain.usecases.todo.AddTodoToList.Arguments
import blog.mateuszgrabarski.todo.domain.usecases.todo.AddTodoToList.Companion.ERROR_LIST_NOT_FOUND
import blog.mateuszgrabarski.todo.domain.usecases.utils.Failure
import blog.mateuszgrabarski.todo.domain.usecases.utils.Result
import blog.mateuszgrabarski.todo.domain.usecases.utils.Success
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AddTodoToListImpl(
    private val todoFactory: TodoFactory,
    private val listRepository: TodoListRepository,
    private val todoRepository: TodoRepository
) : AddTodoToList {

    override suspend fun execute(argument: Arguments): Flow<Result<Todo>> = flow {
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