package blog.mateuszgrabarski.todo.data.store.repositories

import blog.mateuszgrabarski.todo.common.components.DispatcherProvider
import blog.mateuszgrabarski.todo.data.model.mappers.specific.TodoMapper
import blog.mateuszgrabarski.todo.data.store.cache.CacheResult
import blog.mateuszgrabarski.todo.data.store.cache.TodoCacheDataSource
import blog.mateuszgrabarski.todo.data.store.cache.utils.safeCacheCall
import blog.mateuszgrabarski.todo.data.store.network.TodoRemoteDataSource
import blog.mateuszgrabarski.todo.data.store.network.utils.safeApiCall
import blog.mateuszgrabarski.todo.domain.models.Id
import blog.mateuszgrabarski.todo.domain.models.Todo
import blog.mateuszgrabarski.todo.domain.repositories.Success
import blog.mateuszgrabarski.todo.domain.repositories.TodoRepository

class TodoRepositoryImpl(
    private val cache: TodoCacheDataSource,
    private val remote: TodoRemoteDataSource,
    private val mapper: TodoMapper,
    private val dispatcher: DispatcherProvider
) : TodoRepository {

    override suspend fun saveTodo(todo: Todo): Success {
        val entity = mapper.mapToEntity(todo)

        val result = safeCacheCall(dispatcher.io()) {
            cache.insertToNotCompleted(entity)
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

    override suspend fun getById(id: Id): Todo? {
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

    override suspend fun delete(todo: Todo): Success {
        val result = safeCacheCall(dispatcher.io()) {
            if (cache.isCompleted(todo.id)) {
                cache.deleteFromCompleted(todo.id)
            } else {
                cache.deleteFromNotCompleted(todo.id)
            }
        }

        return when (result) {
            is CacheResult.GenericError -> {
                false
            }
            is CacheResult.Success -> {
                safeApiCall(dispatcher.io()) {
                    remote.delete(todo.id)
                }
                true
            }
        }
    }

    override suspend fun markAsCompleted(todo: Todo): Success {
        val result = safeCacheCall(dispatcher.io()) {
            delete(todo)
            cache.insertToCompleted(mapper.mapToEntity(todo))
        }
        return when (result) {
            is CacheResult.GenericError -> false
            is CacheResult.Success -> true
        }
    }

    override suspend fun isCompleted(id: Id): Boolean {
        val result = safeCacheCall(dispatcher.io()) {
            cache.isCompleted(id)
        }
        return when (result) {
            is CacheResult.GenericError -> false
            is CacheResult.Success -> result.value!!
        }
    }

    override suspend fun markAsNotCompleted(todo: Todo): Success {
        val result = safeCacheCall(dispatcher.io()) {
            delete(todo)
            cache.insertToNotCompleted(mapper.mapToEntity(todo))
        }
        return when (result) {
            is CacheResult.GenericError -> false
            is CacheResult.Success -> true
        }
    }

    override suspend fun update(todo: Todo): Success {
        val entity = mapper.mapToEntity(todo)

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
}