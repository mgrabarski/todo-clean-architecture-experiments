package blog.mateuszgrabarski.todo.domain.usecases.todo.impl

import blog.mateuszgrabarski.todo.domain.models.Todo
import blog.mateuszgrabarski.todo.domain.repositories.TodoListRepository
import blog.mateuszgrabarski.todo.domain.repositories.TodoRepository
import blog.mateuszgrabarski.todo.domain.usecases.todo.AddTodoToList
import blog.mateuszgrabarski.todo.domain.usecases.todo.AddTodoToList.Arguments
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AddTodoToListImpl(
    private val listRepository: TodoListRepository,
    private val todoRepository: TodoRepository
) : AddTodoToList {

    override suspend fun execute(argument: Arguments): Flow<Todo> = flow {
        val list = listRepository.getById(argument.listId)

    }
}