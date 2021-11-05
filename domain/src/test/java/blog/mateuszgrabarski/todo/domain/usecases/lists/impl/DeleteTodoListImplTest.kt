package blog.mateuszgrabarski.todo.domain.usecases.lists.impl

import app.cash.turbine.test
import blog.mateuszgrabarski.todo.domain.fakes.FakeToDoListRepository
import blog.mateuszgrabarski.todo.domain.fakes.data.anyTodoList
import blog.mateuszgrabarski.todo.domain.models.Id
import blog.mateuszgrabarski.todo.domain.usecases.lists.DeleteTodoList.Arguments
import blog.mateuszgrabarski.todo.domain.usecases.lists.DeleteTodoList.Companion.ERROR_LIST_NOT_FOUND
import blog.mateuszgrabarski.todo.domain.usecases.utils.Failure
import blog.mateuszgrabarski.todo.domain.usecases.utils.Success
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class DeleteTodoListImplTest {

    private lateinit var repository: FakeToDoListRepository
    private lateinit var sut: DeleteTodoListImpl

    @BeforeEach
    internal fun setUp() {
        repository = FakeToDoListRepository()
        sut = DeleteTodoListImpl(repository)
    }

    @Test
    internal fun `Emits error when list is not available in repo`() = runBlocking {
        sut.execute(
            Arguments(ANY_ID)
        ).test {
            val result = awaitItem() as Failure
            assertEquals(ERROR_LIST_NOT_FOUND, result.message)
            awaitComplete()
        }
    }

    @Test
    internal fun `Deletes todo list`() = runBlocking {
        repository.saveList(anyTodoList(ANY_ID))
        sut.execute(
            Arguments(ANY_ID)
        ).test {
            val result = (awaitItem() as Success<Boolean>).data
            assertTrue(result)
            assertNull(repository.getById(ANY_ID))
            awaitComplete()
        }
    }


    companion object {
        private val ANY_ID = Id.randomUUID()
    }
}