package blog.mateuszgrabarski.todo.domain.repositories

import blog.mateuszgrabarski.todo.domain.models.Id
import blog.mateuszgrabarski.todo.domain.models.Todo

interface TodoRepository {
    suspend fun saveTodo(todo: Todo): Success
    suspend fun getById(id: Id): Todo?
    suspend fun delete(todo: Todo): Success
    suspend fun markAsCompleted(todo: Todo): Success
    suspend fun isCompleted(id: Id): Boolean
    suspend fun markAsNotCompleted(todo: Todo): Success
    suspend fun update(todo: Todo)
}