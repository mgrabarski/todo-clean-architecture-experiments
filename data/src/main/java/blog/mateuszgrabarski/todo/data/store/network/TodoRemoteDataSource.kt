package blog.mateuszgrabarski.todo.data.store.network

import blog.mateuszgrabarski.todo.data.model.entities.TodoEntity
import blog.mateuszgrabarski.todo.domain.models.Id

interface TodoRemoteDataSource {
    suspend fun insert(entity: TodoEntity)
    suspend fun delete(id: Id)
    suspend fun update(entity: TodoEntity)
}