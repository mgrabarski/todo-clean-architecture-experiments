package blog.mateuszgrabarski.todo.data.store.cache

import blog.mateuszgrabarski.todo.data.model.entities.TodoEntity
import blog.mateuszgrabarski.todo.domain.models.Id

interface TodoCacheDataSource {
    suspend fun insertToNotCompleted(entity: TodoEntity)
    suspend fun insertToCompleted(entity: TodoEntity)
    suspend fun get(id: Id): TodoEntity?
    suspend fun update(entity: TodoEntity)
    suspend fun deleteFromCompleted(id: Id)
    suspend fun deleteFromNotCompleted(id: Id)
    suspend fun isCompleted(id: Id): Boolean
}