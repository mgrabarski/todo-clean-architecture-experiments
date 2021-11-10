package blog.mateuszgrabarski.todo.domain.repositories

import blog.mateuszgrabarski.todo.domain.models.Id
import blog.mateuszgrabarski.todo.domain.models.Todo

interface TodoRepository {
    suspend fun saveTodo(todo: Todo)
    suspend fun getById(id: Id): Todo?
    suspend fun delete(todo: Todo)
    suspend fun markAsCompleted(todo: Todo)
}