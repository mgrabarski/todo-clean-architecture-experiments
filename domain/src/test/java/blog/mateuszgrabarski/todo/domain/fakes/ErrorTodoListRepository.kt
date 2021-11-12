package blog.mateuszgrabarski.todo.domain.fakes

import blog.mateuszgrabarski.todo.domain.models.Id
import blog.mateuszgrabarski.todo.domain.models.TodoList
import blog.mateuszgrabarski.todo.domain.repositories.Success
import blog.mateuszgrabarski.todo.domain.repositories.TodoListRepository

class ErrorTodoListRepository : TodoListRepository {
    override suspend fun saveList(newList: TodoList): Success = false

    override suspend fun getById(id: Id): TodoList? = null

    override suspend fun update(list: TodoList): Success = false

    override suspend fun delete(id: Id): Success = false

    override suspend fun getAllLists(): List<TodoList> = emptyList()
}