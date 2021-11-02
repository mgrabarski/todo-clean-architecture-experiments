package blog.mateuszgrabarski.todo.data.store

import blog.mateuszgrabarski.todo.data.store.cache.TodoListCacheDataSource
import blog.mateuszgrabarski.todo.data.store.network.TodoListRemoteDataSource
import blog.mateuszgrabarski.todo.domain.models.Id
import blog.mateuszgrabarski.todo.domain.models.TodoList
import blog.mateuszgrabarski.todo.domain.repositories.TodoListRepository

class TodoListRepositoryImpl(
    private val cacheDataSource: TodoListCacheDataSource,
    private val remoteDataSource: TodoListRemoteDataSource
) : TodoListRepository {

    override suspend fun saveList(newList: TodoList) {
        cacheDataSource.insert(newList)
        remoteDataSource.insert(newList)
    }

    override suspend fun getById(id: Id): TodoList? {
        TODO("Not yet implemented")
    }

    override suspend fun update(list: TodoList) {
        TODO("Not yet implemented")
    }
}