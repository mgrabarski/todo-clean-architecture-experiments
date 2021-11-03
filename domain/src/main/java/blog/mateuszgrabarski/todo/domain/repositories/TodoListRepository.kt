package blog.mateuszgrabarski.todo.domain.repositories

import blog.mateuszgrabarski.todo.domain.models.Id
import blog.mateuszgrabarski.todo.domain.models.TodoList

interface TodoListRepository {
    suspend fun saveList(newList: TodoList)
    suspend fun getById(id: Id): TodoList?
    suspend fun update(list: TodoList)
    suspend fun delete(id: Id)
    suspend fun getAllLists(): List<TodoList>
}