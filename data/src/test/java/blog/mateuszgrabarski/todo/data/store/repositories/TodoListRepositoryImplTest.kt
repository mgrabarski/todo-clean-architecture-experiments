package blog.mateuszgrabarski.todo.data.store.repositories

import blog.mateuszgrabarski.todo.data.model.mappers.specific.DomainEntityMapper
import blog.mateuszgrabarski.todo.data.store.cache.TodoListCacheDataSource
import blog.mateuszgrabarski.todo.data.store.repositories.fakes.FakeTodoListCacheDataSource
import blog.mateuszgrabarski.todo.data.store.repositories.fakes.FakeTodoListRemoteDataSource
import blog.mateuszgrabarski.todo.domain.models.DateTime
import blog.mateuszgrabarski.todo.domain.models.Id
import blog.mateuszgrabarski.todo.domain.models.TodoList
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
internal class TodoListRepositoryImplTest {

    private val cache = FakeTodoListCacheDataSource()
    private val remote = FakeTodoListRemoteDataSource()
    private val mapper = DomainEntityMapper()
    private val dispatcher: CoroutineDispatcher = TestCoroutineDispatcher()

    private val sut = TodoListRepositoryImpl(cache, remote, mapper, dispatcher)

    @Test
    internal fun `Saves todo list`() = runBlocking {
        val todoList = anyTodoList()

        val result = sut.saveList(todoList)

        assertTrue(result)
        assertNotNull(cache.get(todoList.id))
        assertNotNull(remote.get(todoList.id))
    }

    @Test
    internal fun `Returns false when generic error occurred`() = runBlocking {
        val errorCache = mockk<TodoListCacheDataSource>()
        coEvery { errorCache.insert(any()) } throws CancellationException()
        val sut = TodoListRepositoryImpl(errorCache, remote, mapper, dispatcher)

        val result = sut.saveList(anyTodoList())

        assertFalse(result)
    }

    @Test
    internal fun `Updates todo list`() = runBlocking {
        val list = anyTodoList()
        sut.saveList(list)
        list.description = NEW_DESCRIPTION

        val result = sut.update(list)

        assertTrue(result)
        assertEquals(NEW_DESCRIPTION, cache.get(list.id)!!.description)
        assertEquals(NEW_DESCRIPTION, remote.get(list.id)!!.description)
    }

    @Test
    internal fun `Deletes todo`() = runBlocking {
        val todo = anyTodoList()
        sut.saveList(todo)

        val result = sut.delete(todo.id)

        assertTrue(result)
        assertNull(cache.get(todo.id))
        assertNull(remote.get(todo.id))
    }

    companion object {
        private const val NEW_DESCRIPTION = "new description"
    }
}

private fun anyTodoList(id: Id = Id.randomUUID()) = TodoList(
    id = id,
    name = "name",
    description = "description",
    createDate = DateTime.now(),
    modificationDateTime = null
)