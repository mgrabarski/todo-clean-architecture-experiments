package blog.mateuszgrabarski.todo.data.store.cache

import blog.mateuszgrabarski.todo.data.model.entities.TodoEntity
import blog.mateuszgrabarski.todo.domain.models.Id

interface TodoCacheDataSource {
    suspend fun insert(entity: TodoEntity)
    suspend fun get(id: Id): TodoEntity?
    suspend fun update(entity: TodoEntity)
    suspend fun delete(id: Id)
}