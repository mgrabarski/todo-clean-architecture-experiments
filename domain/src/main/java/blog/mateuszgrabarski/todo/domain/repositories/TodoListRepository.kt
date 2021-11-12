package blog.mateuszgrabarski.todo.domain.repositories

import blog.mateuszgrabarski.todo.domain.models.Id
import blog.mateuszgrabarski.todo.domain.models.TodoList

interface TodoListRepository {
    suspend fun saveList(newList: TodoList): Success
    suspend fun getById(id: Id): TodoList?
    suspend fun update(list: TodoList): Success
    suspend fun delete(id: Id): Success
    suspend fun getAllLists(): List<TodoList>
}