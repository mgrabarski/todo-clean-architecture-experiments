package blog.mateuszgrabarski.todo.domain.usecases.lists.impl

import blog.mateuszgrabarski.todo.domain.fakes.FakeToDoListRepository
import blog.mateuszgrabarski.todo.domain.fakes.data.anyTodoList
import blog.mateuszgrabarski.todo.domain.models.Id
import blog.mateuszgrabarski.todo.domain.models.TodoList
import blog.mateuszgrabarski.todo.domain.usecases.lists.GetTodoList.Arguments
import blog.mateuszgrabarski.todo.domain.usecases.lists.GetTodoList.Companion.ERROR_LIST_NOT_FOUND
import blog.mateuszgrabarski.todo.domain.usecases.utils.Failure
import blog.mateuszgrabarski.todo.domain.usecases.utils.Success
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class GetTodoListImplTest {

    private val repository = FakeToDoListRepository()
    private val sut = GetTodoListImpl(repository)

    @Test
    internal fun `Emits error when list is not found`() = runBlocking {
        sut.execute(
            Arguments(ANY_ID)
        ).collect {
            val result = (it as Failure).message
            assertEquals(ERROR_LIST_NOT_FOUND, result)
        }
    }

    @Test
    internal fun `Gets todo list`() = runBlocking {
        repository.saveList(anyTodoList(ANY_ID))
        sut.execute(
            Arguments(ANY_ID)
        ).collect {
            val result = (it as Success<TodoList>).data
            assertEquals(ANY_ID, result.id)
        }
    }

    companion object {
        private val ANY_ID = Id.randomUUID()
    }
}