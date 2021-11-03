package blog.mateuszgrabarski.todo.domain.usecases.lists.impl

import blog.mateuszgrabarski.todo.domain.models.TodoList
import blog.mateuszgrabarski.todo.domain.repositories.TodoListRepository
import blog.mateuszgrabarski.todo.domain.usecases.lists.GetAllTodoLists
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetAllTodoListsImpl(
    private val repository: TodoListRepository
) : GetAllTodoLists {

    override fun execute(): Flow<List<TodoList>> = flow {
        emit(repository.getAllLists())
    }
}