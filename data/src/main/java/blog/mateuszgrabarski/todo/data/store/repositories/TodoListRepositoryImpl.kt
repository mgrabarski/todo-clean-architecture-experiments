package blog.mateuszgrabarski.todo.data.store.repositories

import blog.mateuszgrabarski.todo.common.components.DispatcherProvider
import blog.mateuszgrabarski.todo.data.model.entities.TodoListEntity
import blog.mateuszgrabarski.todo.data.model.mappers.DomainModelMapper
import blog.mateuszgrabarski.todo.data.store.cache.CacheResult
import blog.mateuszgrabarski.todo.data.store.cache.TodoListCacheDataSource
import blog.mateuszgrabarski.todo.data.store.cache.utils.safeCacheCall
import blog.mateuszgrabarski.todo.data.store.network.TodoListRemoteDataSource
import blog.mateuszgrabarski.todo.data.store.network.utils.safeApiCall
import blog.mateuszgrabarski.todo.domain.models.Id
import blog.mateuszgrabarski.todo.domain.models.TodoList
import blog.mateuszgrabarski.todo.domain.repositories.Success
import blog.mateuszgrabarski.todo.domain.repositories.TodoListRepository

class TodoListRepositoryImpl(
    private val cache: TodoListCacheDataSource,
    private val remote: TodoListRemoteDataSource,
    private val mapper: DomainModelMapper<TodoListEntity, TodoList>,
    private val dispatcher: DispatcherProvider
) : TodoListRepository {

    override suspend fun saveList(newList: TodoList): Success {
        val entity = mapper.mapToEntity(newList)

        val result = safeCacheCall(dispatcher.io()) {
            cache.insert(entity)
        }

        return when (result) {
            is CacheResult.GenericError -> {
                false
            }
            is CacheResult.Success -> {
                safeApiCall(dispatcher.io()) {
                    remote.insert(entity)
                }
                true
            }
        }
    }

    override suspend fun getById(id: Id): TodoList? {
        val result = safeCacheCall(dispatcher.io()) {
            cache.get(id)
        }
        return when (result) {
            is CacheResult.GenericError -> {
                null
            }
            is CacheResult.Success -> {
                if (result.value != null) {
                    mapper.mapFromEntity(result.value)
                } else {
                    null
                }
            }
        }
    }

    override suspend fun update(list: TodoList): Success {
        val entity = mapper.mapToEntity(list)

        val result = safeCacheCall(dispatcher.io()) {
            cache.update(entity)
        }
        return when (result) {
            is CacheResult.GenericError -> {
                false
            }
            is CacheResult.Success -> {
                safeApiCall(dispatcher.io()) {
                    remote.update(entity)
                }
                true
            }
        }
    }

    override suspend fun delete(id: Id): Success {
        val result = safeCacheCall(dispatcher.io()) {
            cache.delete(id)
        }

        return when (result) {
            is CacheResult.GenericError -> {
                false
            }
            is CacheResult.Success -> {
                safeApiCall(dispatcher.io()) {
                    remote.delete(id)
                }
                true
            }
        }
    }

    override suspend fun getAllLists(): List<TodoList> {
        val result = safeCacheCall(dispatcher.io()) {
            cache.getAll()
        }
        return when (result) {
            is CacheResult.GenericError -> {
                emptyList()
            }
            is CacheResult.Success -> {
                result.value?.map { mapper.mapFromEntity(it) } ?: emptyList()
            }
        }
    }
}