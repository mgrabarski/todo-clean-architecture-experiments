package blog.mateuszgrabarski.todo.data.store.cache

import blog.mateuszgrabarski.todo.data.model.entities.TodoListEntity
import blog.mateuszgrabarski.todo.domain.models.Id

interface TodoListCacheDataSource {
    suspend fun insert(newList: TodoListEntity)
    suspend fun getAll(): List<TodoListEntity>
    suspend fun get(id: Id): TodoListEntity?
    suspend fun update(list: TodoListEntity)
    suspend fun delete(id: Id)
}