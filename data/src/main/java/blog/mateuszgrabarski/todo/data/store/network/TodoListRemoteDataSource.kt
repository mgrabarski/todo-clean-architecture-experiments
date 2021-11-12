package blog.mateuszgrabarski.todo.data.store.network

import blog.mateuszgrabarski.todo.data.model.entities.TodoListEntity
import blog.mateuszgrabarski.todo.domain.models.Id

interface TodoListRemoteDataSource {
    suspend fun insert(newList: TodoListEntity)
    suspend fun get(id: Id): TodoListEntity?
    suspend fun update(list: TodoListEntity)
    suspend fun delete(id: Id)
}