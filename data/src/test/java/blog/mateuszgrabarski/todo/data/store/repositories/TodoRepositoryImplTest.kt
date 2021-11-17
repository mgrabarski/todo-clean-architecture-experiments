package blog.mateuszgrabarski.todo.data.store.repositories

import blog.mateuszgrabarski.todo.common_test.components.CoroutineDispatcherForTests
import blog.mateuszgrabarski.todo.data.model.mappers.specific.TodoMapper
import blog.mateuszgrabarski.todo.data.store.cache.TodoCacheDataSource
import blog.mateuszgrabarski.todo.data.store.network.TodoRemoteDataSource
import blog.mateuszgrabarski.todo.data.store.repositories.fakes.FakeTodoCacheDataSource
import blog.mateuszgrabarski.todo.data.store.repositories.fakes.FakeTodoRemoteDataSource
import blog.mateuszgrabarski.todo.domain.models.Id
import blog.mateuszgrabarski.todo.domain.models.Todo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.joda.time.DateTime
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
internal class TodoRepositoryImplTest {

    private val cache: TodoCacheDataSource = FakeTodoCacheDataSource()
    private val remote: TodoRemoteDataSource = FakeTodoRemoteDataSource()
    private val mapper: TodoMapper = TodoMapper()
    private val dispatcher = CoroutineDispatcherForTests()

    private val sut = TodoRepositoryImpl(cache, remote, mapper, dispatcher)

    @Test
    internal fun `Inserts todo`() = runBlocking {
        val todo = anyTodo()

        val result = sut.saveTodo(todo)

        assertTrue(result)
        assertEquals(todo.id, cache.get(todo.id)!!.id)
        assertEquals(todo.id, remote.get(todo.id)!!.id)
    }
}

private fun anyTodo(id: Id = Id.randomUUID(), todoListId: Id = Id.randomUUID()) = Todo(
    id = id,
    description = "description",
    createDate = DateTime.now(),
    modificationDate = null,
    todoListId = todoListId
)