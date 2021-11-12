package blog.mateuszgrabarski.todo.domain.usecases.todo.impl

import app.cash.turbine.test
import blog.mateuszgrabarski.todo.domain.data.validators.TodoDescriptionValidator
import blog.mateuszgrabarski.todo.domain.fakes.FakeTodoRepository
import blog.mateuszgrabarski.todo.domain.fakes.data.anyTodo
import blog.mateuszgrabarski.todo.domain.models.Id
import blog.mateuszgrabarski.todo.domain.models.Todo
import blog.mateuszgrabarski.todo.domain.repositories.TodoRepository
import blog.mateuszgrabarski.todo.domain.usecases.todo.UpdateTodo.Arguments
import blog.mateuszgrabarski.todo.domain.usecases.todo.UpdateTodo.Companion.ERROR_EMPTY_DESCRIPTION
import blog.mateuszgrabarski.todo.domain.usecases.todo.UpdateTodo.Companion.ERROR_TODO_NOT_FOUND
import blog.mateuszgrabarski.todo.domain.usecases.todo.UpdateTodo.Companion.ERROR_UNKNOWN
import blog.mateuszgrabarski.todo.domain.usecases.utils.Failure
import blog.mateuszgrabarski.todo.domain.usecases.utils.Success
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class UpdateTodoImplTest {

    private val repository = FakeTodoRepository()
    private val validator = TodoDescriptionValidator()
    private val sut = UpdateTodoImpl(repository, validator)

    @Test
    internal fun `Failures when todo not found`() = runBlocking {
        sut.execute(
            Arguments(
                todoId = ANY_ID,
                description = VALID_DESCRIPTION
            )
        ).test {
            val result = awaitItem() as Failure
            assertEquals(ERROR_TODO_NOT_FOUND, result.message)
            awaitComplete()
        }
    }

    @Test
    internal fun `Failures when new description is not valid`() = runBlocking {
        repository.saveTodo(anyTodo(ANY_ID))

        sut.execute(
            Arguments(
                todoId = ANY_ID,
                description = NOT_VALID_DESCRIPTION
            )
        ).test {
            val result = awaitItem() as Failure
            assertEquals(ERROR_EMPTY_DESCRIPTION, result.message)
            awaitComplete()
        }
    }

    @Test
    internal fun `Updates todo`() = runBlocking {
        val todo = anyTodo(ANY_ID).apply {
            description = "some description"
        }
        repository.saveTodo(todo)

        sut.execute(
            Arguments(
                todoId = ANY_ID,
                description = VALID_DESCRIPTION
            )
        ).test {
            val result = awaitItem() as Success<Todo>
            assertEquals(VALID_DESCRIPTION, result.data.description)
            assertEquals(VALID_DESCRIPTION, repository.getById(todo.id)!!.description)
            awaitComplete()
        }
    }

    @Test
    internal fun `Failures when repo returns no success`() = runBlocking {
        val repo = mockk<TodoRepository> {
            coEvery { getById(any()) } returns anyTodo()
            coEvery { update(any()) } returns false
        }

        UpdateTodoImpl(repo, validator)
            .execute(
                Arguments(
                    todoId = ANY_ID,
                    description = VALID_DESCRIPTION
                )
            ).test {
                val result = awaitItem() as Failure
                assertEquals(ERROR_UNKNOWN, result.message)
                awaitComplete()
            }
    }

    companion object {
        private val ANY_ID = Id.randomUUID()
        private const val VALID_DESCRIPTION = "description"
        private const val NOT_VALID_DESCRIPTION = ""
    }
}