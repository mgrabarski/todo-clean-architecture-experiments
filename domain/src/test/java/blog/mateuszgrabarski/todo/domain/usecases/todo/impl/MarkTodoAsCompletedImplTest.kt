package blog.mateuszgrabarski.todo.domain.usecases.todo.impl

import app.cash.turbine.test
import blog.mateuszgrabarski.todo.domain.fakes.FakeTodoRepository
import blog.mateuszgrabarski.todo.domain.fakes.data.anyTodo
import blog.mateuszgrabarski.todo.domain.models.Id
import blog.mateuszgrabarski.todo.domain.repositories.TodoRepository
import blog.mateuszgrabarski.todo.domain.usecases.todo.MarkTodoAsCompleted.Arguments
import blog.mateuszgrabarski.todo.domain.usecases.todo.MarkTodoAsCompleted.Companion.ERROR_TODO_NOT_FOUND
import blog.mateuszgrabarski.todo.domain.usecases.todo.MarkTodoAsCompleted.Companion.ERROR_UNKNOWN
import blog.mateuszgrabarski.todo.domain.usecases.utils.Failure
import blog.mateuszgrabarski.todo.domain.usecases.utils.Success
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class MarkTodoAsCompletedImplTest {

    private val repository = FakeTodoRepository()
    private val sut = MarkTodoAsCompletedImpl(repository)

    @Test
    internal fun `Failures when no todo found`() = runBlocking {
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
    internal fun `Marks todo as completed`() = runBlocking {
        repository.saveTodo(anyTodo(ANY_ID))
        sut.execute(
            Arguments(
                todoId = ANY_ID
            )
        ).test {
            val result = awaitItem() as Success<Boolean>
            assertTrue(result.data)
            awaitComplete()
        }
    }

    @Test
    internal fun `Failures when repo returns no success`() = runBlocking {
        val repo = mockk<TodoRepository>()
        coEvery { repo.getById(any()) } returns anyTodo()
        coEvery { repo.markAsCompleted(any()) } returns false

        MarkTodoAsCompletedImpl(repo)
            .execute(
                Arguments(
                    todoId = ANY_ID
                )
            ).test {
                val result = awaitItem() as Failure
                assertEquals(ERROR_UNKNOWN, result.message)
                awaitComplete()
            }
    }

    companion object {
        private val ANY_ID = Id.randomUUID()
    }
}