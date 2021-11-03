package blog.mateuszgrabarski.todo.domain.usecases.lists.impl

import blog.mateuszgrabarski.todo.domain.fakes.FakeToDoListRepository
import blog.mateuszgrabarski.todo.domain.fakes.data.anyTodoList
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class GetAllTodoListsImplTest {

    private val repository = FakeToDoListRepository()
    private val sut = GetAllTodoListsImpl(repository)

    @Test
    internal fun `Returns empty list when no lists`() = runBlocking {
        sut.execute().collect {
            assertEquals(0, it.size)
        }
    }

    @Test
    internal fun `Gets all lists`() = runBlocking {
        repository.saveList(anyTodoList())
        sut.execute().collect {
            assertEquals(1, it.size)
        }
    }
}