package blog.mateuszgrabarski.todo.domain.usecases.lists.impl

import blog.mateuszgrabarski.todo.domain.fakes.FakeToDoListRepository
import blog.mateuszgrabarski.todo.domain.fakes.data.anyTodoList
import blog.mateuszgrabarski.todo.domain.models.Id
import blog.mateuszgrabarski.todo.domain.usecases.lists.DeleteTodoList.Arguments
import blog.mateuszgrabarski.todo.domain.usecases.lists.DeleteTodoList.Companion.ERROR_LIST_NOT_FOUND
import blog.mateuszgrabarski.todo.domain.usecases.utils.Failure
import blog.mateuszgrabarski.todo.domain.usecases.utils.Success
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class DeleteTodoListImplTest {

    private val repository = FakeToDoListRepository()
    private val sut = DeleteTodoListImpl(repository)

    @Test
    internal fun `Emits error when list is not available in repo`() = runBlocking {
        sut.execute(
            Arguments(ANY_ID)
        ).collect {
            val result = (it as Failure).message
            assertEquals(ERROR_LIST_NOT_FOUND, result)
        }
    }

    @Test
    internal fun `Deletes todo list`() = runBlocking {
        repository.saveList(anyTodoList(ANY_ID))
        sut.execute(
            Arguments(ANY_ID)
        ).collect {
            val result = (it as Success<Boolean>).data
            assertTrue(result)
            assertNull(repository.getById(ANY_ID))
        }
    }


    companion object {
        private val ANY_ID = Id.randomUUID()
    }
}