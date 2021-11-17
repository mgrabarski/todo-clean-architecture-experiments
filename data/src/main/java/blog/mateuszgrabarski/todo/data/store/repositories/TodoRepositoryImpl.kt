package blog.mateuszgrabarski.todo.data.store.repositories

import blog.mateuszgrabarski.todo.common.components.DispatcherProvider
import blog.mateuszgrabarski.todo.data.model.mappers.specific.TodoMapper
import blog.mateuszgrabarski.todo.data.store.cache.TodoCacheDataSource
import blog.mateuszgrabarski.todo.data.store.network.TodoRemoteDataSource
import blog.mateuszgrabarski.todo.domain.models.Id
import blog.mateuszgrabarski.todo.domain.models.Todo
import blog.mateuszgrabarski.todo.domain.repositories.Success
import blog.mateuszgrabarski.todo.domain.repositories.TodoRepository

class TodoRepositoryImpl(
    private val cache: TodoCacheDataSource,
    private val remote: TodoRemoteDataSource,
    private val mapper: TodoMapper,
    private val dispatcherProvider: DispatcherProvider
) : TodoRepository {

    override suspend fun saveTodo(todo: Todo): Success {
        TODO("Not yet implemented")
    }

    override suspend fun getById(id: Id): Todo? {
        TODO("Not yet implemented")
    }

    override suspend fun delete(todo: Todo): Success {
        TODO("Not yet implemented")
    }

    override suspend fun markAsCompleted(todo: Todo): Success {
        TODO("Not yet implemented")
    }

    override suspend fun isCompleted(id: Id): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun markAsNotCompleted(todo: Todo): Success {
        TODO("Not yet implemented")
    }

    override suspend fun update(todo: Todo): Success {
        TODO("Not yet implemented")
    }
}