package blog.mateuszgrabarski.todo.domain.usecases.lists.impl

import app.cash.turbine.test
import blog.mateuszgrabarski.todo.domain.fakes.FakeToDoListRepository
import blog.mateuszgrabarski.todo.domain.fakes.data.anyTodoList
import blog.mateuszgrabarski.todo.domain.models.TodoList
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class GetAllTodoListsImplTest {

    private val repository = FakeToDoListRepository()
    private val sut = GetAllTodoListsImpl(repository)

    @Test
    internal fun `Returns empty list when no lists`() = runBlocking {
        sut.execute().test {
            val result = awaitItem()
            assertEquals(0, result.size)
            awaitComplete()
        }
    }

    @Test
    internal fun `Gets all lists`() = runBlocking {
        repository.saveList(anyTodoList())
        sut.execute().test {
            val result = awaitItem()
            assertEquals(1, result.size)
            awaitComplete()
        }
    }
}