package blog.mateuszgrabarski.todo.domain.usecases.lists.impl

import app.cash.turbine.test
import blog.mateuszgrabarski.todo.domain.data.validators.TodoListNameValidator
import blog.mateuszgrabarski.todo.domain.fakes.FakeToDoListRepository
import blog.mateuszgrabarski.todo.domain.fakes.data.anyTodoList
import blog.mateuszgrabarski.todo.domain.models.Id
import blog.mateuszgrabarski.todo.domain.models.TodoList
import blog.mateuszgrabarski.todo.domain.repositories.TodoListRepository
import blog.mateuszgrabarski.todo.domain.usecases.lists.UpdateTodoList.Arguments
import blog.mateuszgrabarski.todo.domain.usecases.lists.UpdateTodoList.Companion.ERROR_EMPTY_NAME
import blog.mateuszgrabarski.todo.domain.usecases.lists.UpdateTodoList.Companion.ERROR_LIST_NOT_FOUND
import blog.mateuszgrabarski.todo.domain.usecases.lists.UpdateTodoList.Companion.ERROR_UNKNOWN
import blog.mateuszgrabarski.todo.domain.usecases.utils.Failure
import blog.mateuszgrabarski.todo.domain.usecases.utils.Success
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class UpdateTodoListTest {

    private val nameValidator = TodoListNameValidator()
    private val repository = FakeToDoListRepository()
    private val sut = UpdateTodoListImpl(nameValidator, repository)

    @Test
    internal fun `emits failure when list not found in repository`() = runBlocking {
        sut.execute(
            Arguments(
                listId = ANY_ID,
                newName = NEW_NAME,
                newDescription = NEW_DESCRIPTION
            )
        ).test {
            val result = awaitItem() as Failure
            assertEquals(ERROR_LIST_NOT_FOUND, result.message)
            awaitComplete()
        }
    }

    @Test
    internal fun `Updates list`() = runBlocking {
        repository.saveList(anyTodoList(ANY_ID))
        sut.execute(
            Arguments(
                listId = ANY_ID,
                newName = NEW_NAME,
                newDescription = NEW_DESCRIPTION
            )
        ).test {
            val emittedResult = awaitItem() is Success<TodoList>
            assertTrue(emittedResult)

            val result = repository.getById(ANY_ID)!!

            assertEquals(NEW_NAME, result.name)
            assertEquals(NEW_DESCRIPTION, result.description)
            assertNotNull(result.modificationDateTime)
            awaitComplete()
        }
    }

    @Test
    internal fun `Not allows to update list when new name is empty`() = runBlocking {
        sut.execute(
            Arguments(
                listId = ANY_ID,
                newName = EMPTY_NAME,
                newDescription = NEW_DESCRIPTION
            )
        ).test {
            val result = awaitItem() as Failure
            assertEquals(ERROR_EMPTY_NAME, result.message)
            awaitComplete()
        }
    }

    @Test
    internal fun `Failures when repository return error for update`() = runBlocking {
        val errorRepository = mockk<TodoListRepository>()
        coEvery { errorRepository.getById(any()) } returns anyTodoList()
        coEvery { errorRepository.update(any()) } returns false

        UpdateTodoListImpl(
            nameValidator, errorRepository
        ).execute(
            Arguments(
                listId = ANY_ID,
                newName = NEW_NAME,
                newDescription = NEW_DESCRIPTION
            )
        ).test {
            val result = awaitItem() as Failure
            assertEquals(ERROR_UNKNOWN, result.message)
            awaitComplete()
        }
    }

    companion object {
        private val ANY_ID = Id.randomUUID()
        private const val NEW_NAME = "naw name"
        private const val NEW_DESCRIPTION = "new description"
        private const val EMPTY_NAME = ""
    }
}