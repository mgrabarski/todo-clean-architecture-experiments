package blog.mateuszgrabarski.todo.domain.usecases.lists.impl

import app.cash.turbine.test
import blog.mateuszgrabarski.todo.domain.data.factories.TodoListFactory
import blog.mateuszgrabarski.todo.domain.data.validators.TodoListNameValidator
import blog.mateuszgrabarski.todo.domain.fakes.FakeToDoListRepository
import blog.mateuszgrabarski.todo.domain.models.TodoList
import blog.mateuszgrabarski.todo.domain.repositories.TodoListRepository
import blog.mateuszgrabarski.todo.domain.usecases.lists.CreateNewTodoList.Arguments
import blog.mateuszgrabarski.todo.domain.usecases.lists.CreateNewTodoList.Companion.ERROR_EMPTY_NAME
import blog.mateuszgrabarski.todo.domain.usecases.utils.Failure
import blog.mateuszgrabarski.todo.domain.usecases.utils.Success
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class CreateNewTodoListTest {

    private val nameValidator = TodoListNameValidator()
    private val factory: TodoListFactory = TodoListFactory()
    private val repository: TodoListRepository = FakeToDoListRepository()
    private val sut = CreateNewTodoListImpl(nameValidator, factory, repository)

    @Test
    internal fun `Creates new todo list`() = runBlocking {
        sut.execute(
            Arguments(VALID_NAME, SOME_DESCRIPTION)
        ).test {
            val result = awaitItem() as Success<TodoList>
            val resultNote = result.data

            assertEquals(resultNote, repository.getById(resultNote.id))
            awaitComplete()
        }
    }

    @Test
    internal fun `Error result when create list with not valid name`() = runBlocking {
        sut.execute(
            Arguments(NOT_VALID_NAME, SOME_DESCRIPTION)
        ).test {
            val result = awaitItem() as Failure
            assertEquals(ERROR_EMPTY_NAME, result.message)
            awaitComplete()
        }
    }

    companion object {
        private const val VALID_NAME = "name"
        private const val NOT_VALID_NAME = ""
        private const val SOME_DESCRIPTION = "description"
    }
}