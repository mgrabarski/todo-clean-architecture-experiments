package blog.mateuszgrabarski.todo.data.store.repositories

import blog.mateuszgrabarski.todo.data.model.mappers.specific.DomainEntityMapper
import blog.mateuszgrabarski.todo.data.store.repositories.fakes.FakeTodoListCacheDataSource
import blog.mateuszgrabarski.todo.data.store.repositories.fakes.FakeTodoListRemoteDataSource
import blog.mateuszgrabarski.todo.domain.models.DateTime
import blog.mateuszgrabarski.todo.domain.models.Id
import blog.mateuszgrabarski.todo.domain.models.TodoList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.jupiter.api.Assertions.assertTrue
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
    }
}

private fun anyTodoList(id: Id = Id.randomUUID()) = TodoList(
    id = id,
    name = "name",
    description = "description",
    createDate = DateTime.now(),
    modificationDateTime = null
)