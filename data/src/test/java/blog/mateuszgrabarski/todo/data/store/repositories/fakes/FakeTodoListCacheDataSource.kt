package blog.mateuszgrabarski.todo.data.store.repositories.fakes

import blog.mateuszgrabarski.todo.data.model.entities.TodoListEntity
import blog.mateuszgrabarski.todo.data.store.cache.TodoListCacheDataSource
import blog.mateuszgrabarski.todo.domain.models.Id

class FakeTodoListCacheDataSource : TodoListCacheDataSource {

    private val map = mutableMapOf<Id, TodoListEntity>()

    override suspend fun insert(newList: TodoListEntity) {
        map[newList.id] = newList
    }

    override suspend fun getAll(): List<TodoListEntity> = map.values.toList()

    override suspend fun get(id: Id): TodoListEntity? = map[id]

    override suspend fun update(list: TodoListEntity) {
        map[list.id] = list
    }

    override suspend fun delete(id: Id) {
        map.remove(id)
    }
}