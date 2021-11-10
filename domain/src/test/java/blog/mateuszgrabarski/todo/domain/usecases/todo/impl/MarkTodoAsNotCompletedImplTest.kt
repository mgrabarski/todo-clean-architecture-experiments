package blog.mateuszgrabarski.todo.domain.usecases.todo.impl

import app.cash.turbine.test
import blog.mateuszgrabarski.todo.domain.fakes.FakeTodoRepository
import blog.mateuszgrabarski.todo.domain.fakes.data.anyTodo
import blog.mateuszgrabarski.todo.domain.models.Id
import blog.mateuszgrabarski.todo.domain.usecases.todo.MarkTodoAsNotCompleted.Arguments
import blog.mateuszgrabarski.todo.domain.usecases.todo.MarkTodoAsNotCompleted.Companion.ERROR_ALREADY_COMPLETED
import blog.mateuszgrabarski.todo.domain.usecases.todo.MarkTodoAsNotCompleted.Companion.ERROR_TODO_NOT_FOUND
import blog.mateuszgrabarski.todo.domain.usecases.utils.Failure
import blog.mateuszgrabarski.todo.domain.usecases.utils.Success
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class MarkTodoAsNotCompletedImplTest {

    private val repository = FakeTodoRepository()
    private val sut = MarkTodoAsNotCompletedImpl(repository)

    @BeforeEach
    internal fun setUp() {
        repository.reset()
    }

    @Test
    internal fun `Failures when todo not found`() = runBlocking {
        sut.execute(
            Arguments(
                todoId = ANY_ID
            )
        ).test {
            val result = awaitItem() as Failure
            assertEquals(ERROR_TODO_NOT_FOUND, result.message)
            awaitComplete()
        }
    }

    @Test
    internal fun `Failures when task is not completed before mark it as completed`() = runBlocking {
        repository.saveTodo(anyTodo(ANY_ID))
        sut.execute(
            Arguments(
                todoId = ANY_ID
            )
        ).test {
            val result = awaitItem() as Failure
            assertEquals(ERROR_ALREADY_COMPLETED, result.message)
            awaitComplete()
        }
    }

    @Test
    internal fun `Marks todo as not completed`() = runBlocking {
        val todo = anyTodo(ANY_ID)
        repository.saveTodo(todo)
        repository.markAsCompleted(todo)

        sut.execute(
            Arguments(
                todoId = ANY_ID
            )
        ).test {
            val result = awaitItem() as Success<Boolean>
            assertTrue(result.data)
            assertFalse(repository.isCompleted(ANY_ID))
            awaitComplete()
        }
    }

    companion object {
        private val ANY_ID = Id.randomUUID()
    }
}