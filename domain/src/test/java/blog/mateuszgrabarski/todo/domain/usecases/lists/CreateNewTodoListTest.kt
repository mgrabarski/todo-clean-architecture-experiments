package blog.mateuszgrabarski.todo.domain.usecases.lists

import blog.mateuszgrabarski.todo.domain.data.factories.TodoListFactory
import blog.mateuszgrabarski.todo.domain.data.validators.TodoListNameValidator
import blog.mateuszgrabarski.todo.domain.fakes.FakeToDoListRepository
import blog.mateuszgrabarski.todo.domain.models.TodoList
import blog.mateuszgrabarski.todo.domain.repositories.TodoListRepository
import blog.mateuszgrabarski.todo.domain.usecases.lists.CreateNewTodoList.Arguments
import blog.mateuszgrabarski.todo.domain.usecases.lists.CreateNewTodoList.Companion.ERROR_EMPTY_NAME
import blog.mateuszgrabarski.todo.domain.usecases.lists.impl.CreateNewTodoListImpl
import blog.mateuszgrabarski.todo.domain.usecases.utils.Failure
import blog.mateuszgrabarski.todo.domain.usecases.utils.Success
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
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
        ).collect {
            assertTrue(it is Success<TodoList>)

            val resultNote = (it as Success<TodoList>).data

            assertEquals(resultNote, repository.getById(resultNote.id))
        }
    }

    @Test
    internal fun `Error result when create list with not valid name`() = runBlocking {
        sut.execute(
            Arguments(NOT_VALID_NAME, SOME_DESCRIPTION)
        ).collect {
            assertTrue(it is Failure)
            assertEquals(ERROR_EMPTY_NAME, (it as Failure).message)
        }
    }

    companion object {
        private const val VALID_NAME = "name"
        private const val NOT_VALID_NAME = ""
        private const val SOME_DESCRIPTION = "description"
    }
}