package blog.mateuszgrabarski.todo.domain.usecases.todo.impl

import app.cash.turbine.test
import blog.mateuszgrabarski.todo.domain.fakes.FakeTodoRepository
import blog.mateuszgrabarski.todo.domain.fakes.data.anyTodo
import blog.mateuszgrabarski.todo.domain.models.Id
import blog.mateuszgrabarski.todo.domain.repositories.TodoRepository
import blog.mateuszgrabarski.todo.domain.usecases.todo.DeleteTodo
import blog.mateuszgrabarski.todo.domain.usecases.todo.DeleteTodo.Arguments
import blog.mateuszgrabarski.todo.domain.usecases.todo.DeleteTodo.Companion.ERROR_TODO_NOT_FOUND
import blog.mateuszgrabarski.todo.domain.usecases.utils.Failure
import blog.mateuszgrabarski.todo.domain.usecases.utils.Success
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class DeleteTodoImplTest {

    private lateinit var repository: TodoRepository
    private lateinit var sut: DeleteTodo

    @BeforeEach
    internal fun setUp() {
        repository = FakeTodoRepository()
        sut = DeleteTodoImpl(repository)
    }

    @Test
    internal fun `Deletes todo`() = runBlocking {
        repository.saveTodo(anyTodo(id = ANY_ID))
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
    internal fun `Failure when todo not found`() = runBlocking {
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

    companion object {
        private val ANY_ID = Id.randomUUID()
    }
}